# 粘液科技附属 - 模版
这是一个简单的粘液科技附属模版。在右上角有一个绿色的"Use this template"按钮，点击该按钮使用该模版来创建你的附属。

## 如何创建你的附属
这是一个模版仓库，你可以用它来创建你自己的粘液科技附属。  
我们也编写了一个较为详细的、循序渐进的开发指南，你可以在此查看：  
https://slimefun-wiki.guizhanss.cn/Developer-Guide

## 一些重要的部分需要修改
打开文件夹 `src/main/java` 并重命名包名和主类名。  
建议: "me.你的名称.你的项目" (全部小写) 以及 "项目名称.java"  
例如: "me.ybw0014.cooladdon" 以及 "CoolAddon.java"

打开文件 `src/main/resources/plugin.yml` 并更改 "author" 为你的名字，"main" 为主类的路径。  
你可能还需要改变"description"并提供一个有意义的描述。

打开文件 `pom.xml` 并更改 group id 为 "me.%你的名字%" 并修改 artifact id 为你项目的名称。

做完这些，你就可以开始编写剩余的部分了。
