# 简单介绍

使用SpringBoot特性，在SpringBoot应用程序加载时，自动加载指定文件夹的数据，并注入到返回视图的数据中，在视图层可以使用到指定的数据。对开发时的一些静态数据有较好的作用。

# 使用

在maven或gradle中加入依赖即可。

```gradle
repository {
    maven { url "https://github.com/hskill/maven-repo/raw/repository/snapshots"}
}
```

加载依赖
```
dependencies {
    compile 'info.ideatower.springboot:infox:0.1.0-SNAPSHOT'
}
```

将自动的读取项目resources目录下的infox文件夹下的所有以`yml`与`yaml`结尾的数据。

读取的数据在模板中使用时，以`infox.{filename}.{item}`形式使用。其中`infox`是数据前辍，filename是文件名，item是文件中的数据节点。

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

# 配置使用

可以在SpringBoot项目`application.yml`配置文件中进行配置

```yaml
infox:
  mark: infox
  path: infox
```

- `mark` 指在模板中使用的数据前辍
- `path` 指在项目资源文件中的路径（类路径）