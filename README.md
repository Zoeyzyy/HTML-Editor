# HTML-Editor
## 事项
系统测试开始时间：11月17日

## 项目结构
src.main.java       源代码路径

src.main.resources  资源路径

src.main.test       测试路径 


## 提交代码说明

项目的主分支是`main`分支，如果你需要为项目添加新的功能或者修复某些BUG，都需要基于`main`分支创建一个新的分支，在新的分支上进行修改。

添加新功能，请将新分支命名为：`feat/xxx_xxx`。比如：`feat/add_login`，这样从分支名就可以看出来你添加的新功能就是登录功能。

修复BUG，请将新分支命名为：`fix/xxx_xxx`。比如：`fix/login_failed`，这样从分支名就可以看出来你修复的BUG是登录失败。

完成代码后，需要将你的代码PR到`main`分支上，审核通过后就可以在`main`中看到你的代码了（不要直接推送到`master`）。

如果你对GIT还不熟悉，请查看：[Git教程 - 廖雪峰的官方网站 (liaoxuefeng.com)](https://www.liaoxuefeng.com/wiki/896043488029600)

## 模块设计

### Document设计

1. 类的基本结构：
    * 这是一个用于处理HTML文档的Java类，使用了@Data注解（来自Lombok）来自动生成getter/setter等方法
    * 主要用于管理HTML元素树形结构，提供元素的增删改查等操作

2. 核心属性：

    ```
    java
    private HTMLElement root;      // HTML文档的根节点  
    private boolean showID;        // 是否显示ID的标志  
    private StringBuilder sb;      // 用于构建字符串输出的StringBuilder  
    ```

3. 主要依赖关系：
    * HTMLElement类（未在代码中显示，但是核心依赖）

        * 包含了标签名、ID、文本内容和子元素列表等属性
        * 提供了元素的基本操作方法
    * 异常类：

        * ElementBadRemoved：处理错误移除元素的异常
        * ElementNotFound：处理元素未找到的异常

    * Lombok库：

        * 使用@Data注解简化代码
        * 用于自动生成getter/setter等方法

4. 主要功能方法：
    文档操作：
    ```
    java
    public void init()            // 初始化文档  
    public void read(String file) // 读取文件（待实现）  
    public String save()          // 保存文档（待实现）  
    ```
    元素操作：
    ```
    java
    public HTMLElement findElementById(String id)  // 查找元素  
    public void appendElement(...)                 // 添加新元素  
    public void removeElementById(String id)       // 删除元素  
    public void editID(String oldID, String newID) // 修改元素ID  
    ```
    格式化输出：

    ```
    java
    public String getIndentFormat(int indent)    // 获取缩进格式的HTML  
    public String getTreeFormat(boolean spellCheck) // 获取树形格式的HTML  
    ```

5. 设计特点：
    * 采用树形结构存储HTML元素
    * 使用递归方法遍历和处理元素树
    * 提供多种格式化输出方式
    * 实现了基本的元素CRUD操作
    * 使用StringBuilder优化字符串拼接性能

6. 设计模式：
    * 使用了Builder模式（通过HTMLElement.builder()创建元素）
    * 采用了组合模式（Composite Pattern）来构建元素树结构

### Controller设计

1. 整体架构

    该模块采用命令模式（Command Pattern）和工厂模式（Factory Pattern）的组合设计，主要用于处理用户输入的命令解析和执行。整体架构包含两个主要类：
    - `CommandController`：命令控制器，负责接收和解析用户输入
    - `CommandFactory`：命令工厂，负责创建具体的命令对象

2. 类详细设计

    `CommandController` 类
 
    **职责**：
    - 接收用户输入的命令字符串
    - 解析命令字符串为命令名和参数
    - 通过工厂创建对应的命令对象
    - 执行命令并处理可能的异常

    **主要方法**：
    - `run(String input)`：执行命令的主要入口
    - `parseInput(String input)`：解析输入字符串为命令数组

    `CommandFactory` 类
 
    **职责**：
    - 管理所有可用的命令
    - 验证命令格式
    - 创建具体的命令对象

    **内部类**：

    - `CommandFormat`：定义命令格式（参数个数和使用说明）
    - `CommandCreator`：函数式接口，用于创建命令对象

    **主要方法**：

    - `registerCommands()`：注册所有可用命令
    - `createCommand()`：创建具体的命令对象
    - `validateCommandFormat()`：验证命令格式

3. 支持的命令
    模块支持以下命令操作：
    1. `insert`：插入元素
    2. `append`：追加元素
    3. `edit-id`：编辑ID
    4. `edit-text`：编辑文本
    5. `delete`：删除元素
    6. `print-indent`：打印缩进视图
    7. `print-tree`：打印树形结构
    8. `spell-check`：拼写检查
    9. `read`：读取文件
    10. `save`：保存文件
    11. `init`：初始化
    12. `redo`：重做
    13. `undo`：撤销

4. 依赖关系

    外部依赖：
    - `HTMLDocument`：文档对象，所有命令操作的目标对象
    - 各种具体的Command实现类（如InsertCommand、DeleteCommand等）

    内部依赖：
    ```
    CommandController
        └── CommandFactory
            ├── CommandFormat (内部类)
            └── CommandCreator (函数式接口)
    ```

5. 设计特点

    1. **可扩展性**：
        - 通过命令模式实现了命令的封装
        - 使用工厂模式集中管理命令的创建
        - 新增命令只需要实现Command接口并在工厂中注册

    2. **健壮性**：
        - 完善的参数验证机制
        - 统一的异常处理
        - 命令格式的严格定义

    3. **灵活性**：
        - 使用函数式接口简化命令创建
        - 支持可选参数
        - 统一的命令格式定义

    4. **维护性**：
        - 清晰的职责划分
        - 集中的命令管理
        - 标准化的命令格式验证
