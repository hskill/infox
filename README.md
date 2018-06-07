# 简单介绍

使用SpringBoot特性，在SpringBoot应用程序加载时，自动加载指定文件夹的数据，并注入到返回视图的数据中，在视图层可以使用到指定的数据。对开发时的一些静态数据有较好的作用。

# 使用

## 加入依赖

在maven或gradle中加入依赖即可。

```gradle
repository {
    maven { url "https://github.com/hskill/maven-repo/raw/repository/snapshots" }
}
```

加载依赖
```
dependencies {
    compile 'info.ideatower.springboot:infox:0.1.0-SNAPSHOT'
}
```

将该库加入到springboot项目后（参与：springboot 配置使用），可以自动的读取项目resources目录下的infox文件夹下的所有以`yml`与`yaml`结尾的数据文件。


## 模板文件使用

在springboot项目中，可以自动的在视图渲染前，将数据加入 Model 中。

读取的数据在模板中使用时，以`infox.{filename}.{item}`形式使用。其中`infox`是数据前辍，filename是数据文件名（无需加文件名后辍），item是文件中的数据节点。

注意，filename读取数据文件为Map对象，所以可以使用多级节点，类似 `infox.{filename}.{foo}.{bar}`

例如，在infox文件夹中，放置 `global.yaml` 文件

```yaml
title: hello world
```

在模板（以freemark为例）使用

```html
<h1>${infox.global.title}</h1>
```
渲染 ==>
```html
<h1>hello world</h1>
```

## json 使用

在springboot项目中，可以自动开启restful访问，获取某一文件的数据。

通过 `/infox/{filename}` 访问，其中 filename 是数据文件名（无需加文件名后辍）。

注意，只能通过整个文件访问的形式，不能访问节点数据。

例如，访问 `/infox/global`

```json
{
    'code': '000000',
    'message': 'okay',
    'data': {
        'title': 'Hello World'
    }
}
```

## 应用程序使用

```java

// 获取所有值
Map<String, Object> allData = Infox.getAll();

// 按文件名，获取数据
Map<String, Object> globalData = Infox.get("global");

```



# springboot 配置使用

可以在SpringBoot项目`application.yml`配置文件中进行配置

```yaml
infox:
  mark: infox
  path: infox
```

- `mark` 指在模板中使用的数据前辍
- `path` 指在项目资源文件中的路径（类路径）

# 使用场景

主要为后端开发提供只读静态数据。大部分情况下，可以在运行时固定，但软件生存周期内不固定的数据情况下使用。

## （1）开发时使用infox替代数据库数据，加快原型系统开发

可以使用infox静态数据替代动态的数据库数据，加快开发。应用程序完整后，再改为数据库数据使用。

## （2）直接使用infox数据提供内容

可以在一些简单系统中，如简单的内容系统（页面不多），编写前端html，直接使用infox提供数据，如有数据更改，更改数据文件后重启。

例如，编写app下载页面（单个页面），将说明、下载链接等数据通过json加载。

## （2）替换视图层中的文字内容

原本写在视图层文件的文字内容，可以提取为变量，加在数据文件中，通过后端渲染引擎或json来显示。主要能方便维护开发。

如新增 global.yml 文件

```yml
title: 全域集游宝
simple: 集游宝
version: v0.1.0
company:
  name: 贵州中测信息技术有限公司
  simple: 中测信息
  url: http://iwiteks.com
```

## （3）替代某些不需要存储在数据库中的数据

某些数据量小、无需数据库动态管理、也无需开发管理视图进行数据管理，如后台菜单导航、相关程序设置等。

## （4）应用内置数据

例如，与应用程序相关的模块或功能开关配置。

# 其它问题

## （1）认证或鉴权

目前，数据的json访问无需认证或鉴权，所以敏感数据，如密码、加密密钥，请不要放置在数据文件中。

## （2）json 还是 yaml

yaml优点：yaml文件紧凑、可以写注释，比较适合整理数据文件。

yaml缺点：不能直接为web前端使用。



json优点：直接把数据放在public文件夹，前端就可以使用

json缺点：除了对比yaml的优点外，放在public文件夹的json文件无法为后端程序使用（所以本项目大部分情况下，主要是为后端开发服务）

## （3）yaml的校验与运行时可能会发生异常的问题

xml文件有很大的弱点，就是很冗长，不管是写spring配置、还是maven配置，看着就头晕。

但不管怎么样，xml文件有一个极其的优点，就是可以写dtd进行校验。可以在运行前检查，降低开发人员自已“无心之失”，并消减运行时发生异常的可能性。

json或yaml无法校验，所以往往在运行后，才能通过异常，得知问题已经发生。

