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

### SpellChecker

## 运行步骤
### 语言版本
* JDK 8
* JDK 11

### 依赖安装
* pom.xml：
  * languagetool
  * jsoup
  * lombok
  * mockito
  * junit