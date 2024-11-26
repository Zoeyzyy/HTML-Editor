# Lab1 : HTML-Editor
## 功能模块总述

## 功能模块描述
### Console
* 需求简述：
  * 读取用户输入
  * 返回对应的输出或报错
* 功能开发逻辑：
  1. 初始化
  2. 读取用户输入
  3. 解析为命令
  4. 运行命令
  5. 返回对应的输出或报错

### CommandParser

### CommandInvoker
* 需求简述：
  * 执行命令
  * 存储命令
* 功能开发逻辑：
  1. 执行命令
  2. 在对应的CommandHistory中存储命令

### CommandHistory

### Command
需求1
* 需求简述：
  * 执行指令
  * 保存undo所需的内容
* 功能开发逻辑：
  * 抽象接口：execute()

需求2 (UndoableCommand)
* 需求简述：
  * 撤销指令
* 功能开发逻辑：
  * 抽象接口：undo()



### Session

### Editor

### HTMLDocument

### HTMLElement
#### 需求简述：
本实验旨在通过Builder模式实现HTML元素的高效构造与操作，以简化复杂HTML结构的创建过程，同时支持基本的HTML节点操作（如插入、删除、兄弟节点链接更新等）。实验的具体目标包括：
1. 实现一个支持延迟初始化和线程安全的HTML节点树；
2. 提供灵活的Builder模式，简化HTML节点的构造与操作；
3. 通过功能接口扩展性，支持自定义操作（如拼写检查和元素位置插入）。
4. 特殊要求：为了简化要求，本次 Lab 中对于 HTML 的约定如下：
（1）`<html>`, `<head>`,` <title>` ,` <body>`四个标签有且仅有一个。HTML 文件顶层是`<html>`元素，其只有两个子元素，依序为`<head>`和`<body>`。同时，`<title>`是`<head>`的子元素。结构如下：

```html
<html>
  <head>
    <title>My Webpage</title>
  </head>
  <body>
    ...
  </body>
</html>
```
（2）元素内部可以包含文本内容，但文本必须处在该元素的其他子元素之前（参见上述例子中的`<div id="footer">`）。
（3）属性只支持 id，除`<html>`, `<head>`,` <title>` ,` <body>`外其他元素必须拥有 id 属性，若这四个标签没有提供 id，默认 id 为标签名。同时 id 应该具有唯一性。
#### 功能开发逻辑：
（1）功能模块划分：

代码主要分为两个核心模块：
1.1 **抽象基类 `HTMLElement`**：
    - 定义HTML节点的基本结构与行为。
    - 提供Builder接口定义以及通用的逻辑实现（如延迟初始化、子节点管理）。
1.2 **具体实现类 `HTMLElementImpl`**：
    - 实现具体的HTML节点行为（如插入、删除、展示）。
    - 实现Builder模式的具体逻辑。

##### Builder模式的逻辑与实现
通过Builder模式，我们能够灵活构造复杂的HTML节点树，避免直接使用构造函数导致的代码冗长。核心逻辑如下：
1. **抽象接口定义**：
   在`HTMLElement`中定义Builder接口，使得构造行为与节点类分离。
2. **具体实现类 `HTMLElementImpl.BuilderImpl`**：
   提供具体的构造逻辑，允许按需设置`tagName`、`id`、`className`等属性。
   使用链式方法调用，提高代码可读性。
3. **拼写检查扩展**：
   在构造完成后，通过`SpellChecker`对文本内容进行拼写检查并存储结果。

##### 延迟初始化与多线程安全
1. **延迟初始化 `initializeChildren`**：
   在子节点首次访问时，延迟初始化`children`列表，避免不必要的资源消耗。
   引入“哨兵节点”(`start`和`tail`)，简化节点操作逻辑。
2. **多线程安全**：
   使用`volatile`和`synchronized`保证`children`的线程安全初始化，避免重复操作。

##### HTML节点树的操作
1.插入操作
- **位置插入（`insertElementBefore`）**：
  通过目标ID定位插入位置，在目标节点之前插入新节点。
  更新节点的`parent`、兄弟节点指针、索引等信息。
- **默认尾部插入**：
  若目标ID为空，则自动插入到尾部节点前。

2.删除操作
- **通过实例删除（`removeChild`）**：
  根据传入的节点实例，更新父节点、兄弟节点指针并从子节点列表中移除。
- **通过ID删除（`removeChild(String id)`）**：
  根据ID定位并删除指定节点。

3.拼写检查功能
- 利用单例模式的`SpellCheckerManager`对文本内容进行检查，返回修正后的结果。
- 拼写检查结果通过`setSpellCheckResults`存储，支持后续操作。

### SpellChecker
* 需求简述：选择合适的拼写检查服务，实现对元素中的文本内容进行拼写检查，并报告错误.
* 功能开发逻辑：
##### 1.**拼写检查集成**
在`build`方法中调用`SpellChecker`对文本内容进行拼写检查。
修正后的文本存储在`HTMLElement`实例中，确保拼写正确。

##### 2. 拼写检查功能开发

(1) **拼写检查器实现**
使用LanguageTool库的`JLanguageTool`实现拼写检查功能。
遍历拼写错误，利用LanguageTool提供的建议修正文本。
修正逻辑采用倒序替换，避免因位置偏移导致错误替换。

(2) **单例模式优化**
在`SpellCheckerManager`中，使用双重检查锁机制实现线程安全的单例模式。
确保拼写检查器`SpellChecker`的实例唯一性，避免资源浪费。
通过`getInstance`方法提供全局访问点，方便在多模块中使用。
>>>>>>> 7edc62df181180db045edeec839cee44d09c4683
