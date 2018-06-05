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

在springboot项目中，可以自动的在视图渲染前，将数据加入Model中。

读取的数据在模板中使用时，以`infox.{filename}.{item}`形式使用。其中`infox`是数据前辍，filename是数据文件名（无需加文件名后辍），item是文件中的数据节点。

注意，filename读取数据文件为Map对象，所以可以使用多级节点，类似 `infox.{filename}.{foo}.{bar}`

例如

在infox文件夹中，放置global.yaml文件

```yaml

title: hello world
```

在模板（以freemark为例）使用

```html
<h1>${infox.global.title}</h1>
```
==>
```html
<h1>hello world</h1>
```

## json 使用

在springboot项目中，可以自动开启restful访问，获取某一文件的数据。

通过 `/infox/{filename}` 访问，其中 filename 是数据文件名（无需加文件名后辍）。

注意，只能通过整个文件访问的形式，不能访问节点数据。

例如

`/infox/global`

```json

{
    code: '000000',
    message: 'okay',
    data: {
        title: 'FooBar'
    }
}

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

## 视图层开发时使用infox替代数据库数据

可以使用静态数据替代动态的数据库数据，加快开发

## 替换视图层中的文字内容

原本写在视图层文件的文字内容，可以提取为变量，加在数据文件中，通过后端渲染引擎或json来显示。主要能方便维护开发。

## 替代某些不需要存储在数据库中的数据

某些数据量小、无需数据库动态管理、也无需开发管理视图进行数据管理，如后台菜单导航、相关程序设置等。