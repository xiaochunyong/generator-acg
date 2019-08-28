# spring-boot-with-kotlin-archetype

## 多模块(Entity单独作为一个模块)
1. 创建并安装archetype
```
git clone https://github.com/xiaochunyong/spring-boot-with-kotlin.git
./create-archetype.sh
```

2. 使用archetype创建新项目
```
mvn archetype:generate -DarchetypeCatalog=local
```

3. 修改application.yml 配置datasource

4. 启动${packageName}.Application


## 单模块
1. 创建并安装archetype
```
git clone https://github.com/xiaochunyong/spring-boot-with-kotlin.git
git checkout single-module
./create-archetype.sh
```

2. 使用archetype创建新项目
```
mvn archetype:generate -DarchetypeCatalog=local
```

### 引用
* [create-from-project-mojo](https://maven.apache.org/archetype/maven-archetype-plugin/create-from-project-mojo.html)
* [create-multi-module-project](https://maven.apache.org/archetype/maven-archetype-plugin/examples/create-multi-module-project.html)
* [create-with-property-file](https://maven.apache.org/archetype/maven-archetype-plugin/examples/create-with-property-file.html)


### 问题
1. 创建多模块项目后运行Application报错`Error:(4, 39) Kotlin: Unresolved reference: User_`
![Q1](docs/WX20190517-120552.png)