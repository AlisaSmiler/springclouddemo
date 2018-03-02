# springclouddemo
分布式微服务demo

坑1：项目启动报错
[ERROR][2018-03-02 11:30:52,181]: - [Tomcat-startStop-1][o.a.c.c.ContainerBase.log:181] - A child container failed during start
java.util.concurrent.ExecutionException: org.apache.catalina.LifecycleException: Failed to start component [StandardEngine[Tomcat].StandardHost[localhost].TomcatEmbeddedContext[]]
	at java.util.concurrent.FutureTask.report(FutureTask.java:122)
	at java.util.concurrent.FutureTask.get(FutureTask.java:192)
	at org.apache.catalina.core.ContainerBase.startInternal(ContainerBase.java:939)
	at org.apache.catalina.core.StandardHost.startInternal(StandardHost.java:872)
	at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:150)
	at org.apache.catalina.core.ContainerBase$StartChild.call(ContainerBase.java:1419)
	at org.apache.catalina.core.ContainerBase$StartChild.call(ContainerBase.java:1409)
	at java.util.concurrent.FutureTask.run(FutureTask.java:266)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
	at java.lang.Thread.run(Thread.java:745)
Caused by: org.apache.catalina.LifecycleException: Failed to start component [StandardEngine[Tomcat].StandardHost[localhost].TomcatEmbeddedContext[]]
	at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:167)
	... 6 common frames omitted
Caused by: org.apache.catalina.LifecycleException: Failed to start component [Pipeline[StandardEngine[Tomcat].StandardHost[localhost].TomcatEmbeddedContext[]]]
	at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:167)
	at org.apache.catalina.core.StandardContext.startInternal(StandardContext.java:5117)
	at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:150)
	... 6 common frames omitted
Caused by: org.apache.catalina.LifecycleException: Failed to start component [org.apache.catalina.authenticator.NonLoginAuthenticator[]]
	at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:167)
	at org.apache.catalina.core.StandardPipeline.startInternal(StandardPipeline.java:182)
	at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:150)
	... 8 common frames omitted
Caused by: java.lang.NoSuchMethodError: javax.servlet.ServletContext.getVirtualServerName()Ljava/lang/String;
	at org.apache.catalina.authenticator.AuthenticatorBase.startInternal(AuthenticatorBase.java:1141)
	at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:150)
	... 10 common frames omitted
[ERROR][2018-03-02 11:30:52,181]: - [main][o.a.c.c.ContainerBase.log:181] - A child container failed during start
java.util.concurrent.ExecutionException: org.apache.catalina.LifecycleException: Failed to start component [StandardEngine[Tomcat].StandardHost[localhost]]
	at java.util.concurrent.FutureTask.report(FutureTask.java:122)
	at java.util.concurrent.FutureTask.get(FutureTask.java:192)
	at org.apache.catalina.core.ContainerBase.startInternal(ContainerBase.java:939)
	at org.apache.catalina.core.StandardEngine.startInternal(StandardEngine.java:262)
	at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:150)
	at org.apache.catalina.core.StandardService.startInternal(StandardService.java:422)
	at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:150)
	at org.apache.catalina.core.StandardServer.startInternal(StandardServer.java:793)
	at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:150)
	at org.apache.catalina.startup.Tomcat.start(Tomcat.java:344)
	at org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer.initialize(TomcatEmbeddedServletContainer.java:99)
	at org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer.<init>(TomcatEmbeddedServletContainer.java:84)
	at org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory.getTomcatEmbeddedServletContainer(TomcatEmbeddedServletContainerFactory.java:554)
	at org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory.getEmbeddedServletContainer(TomcatEmbeddedServletContainerFactory.java:179)
	at org.springframework.boot.context.embedded.EmbeddedWebApplicationContext.createEmbeddedServletContainer(EmbeddedWebApplicationContext.java:164)
	at org.springframework.boot.context.embedded.EmbeddedWebApplicationContext.onRefresh(EmbeddedWebApplicationContext.java:134)
	at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:537)
	at org.springframework.boot.context.embedded.EmbeddedWebApplicationContext.refresh(EmbeddedWebApplicationContext.java:122)
	at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:693)
	at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:360)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:303)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1118)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1107)
	at com.xiajianhx.demo.springcloud.netflix.server.Application.main(Application.java:12)
Caused by: org.apache.catalina.LifecycleException: Failed to start component [StandardEngine[Tomcat].StandardHost[localhost]]
	at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:167)
	at org.apache.catalina.core.ContainerBase$StartChild.call(ContainerBase.java:1419)
	at org.apache.catalina.core.ContainerBase$StartChild.call(ContainerBase.java:1409)
	at java.util.concurrent.FutureTask.run(FutureTask.java:266)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
	at java.lang.Thread.run(Thread.java:745)
Caused by: org.apache.catalina.LifecycleException: A child container failed during start
	at org.apache.catalina.core.ContainerBase.startInternal(ContainerBase.java:947)
	at org.apache.catalina.core.StandardHost.startInternal(StandardHost.java:872)
	at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:150)
	... 6 common frames omitted
[ERROR][2018-03-02 11:30:52,196]: - [main][o.s.b.SpringApplication.reportFailure:771] - Application startup failed
org.springframework.context.ApplicationContextException: Unable to start embedded container; nested exception is org.springframework.boot.context.embedded.EmbeddedServletContainerException: Unable to start embedded Tomcat
	at org.springframework.boot.context.embedded.EmbeddedWebApplicationContext.onRefresh(EmbeddedWebApplicationContext.java:137)
	at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:537)
	at org.springframework.boot.context.embedded.EmbeddedWebApplicationContext.refresh(EmbeddedWebApplicationContext.java:122)
	at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:693)
	at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:360)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:303)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1118)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1107)
	at com.xiajianhx.demo.springcloud.netflix.server.Application.main(Application.java:12)
Caused by: org.springframework.boot.context.embedded.EmbeddedServletContainerException: Unable to start embedded Tomcat
	at org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer.initialize(TomcatEmbeddedServletContainer.java:123)
	at org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer.<init>(TomcatEmbeddedServletContainer.java:84)
	at org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory.getTomcatEmbeddedServletContainer(TomcatEmbeddedServletContainerFactory.java:554)
	at org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory.getEmbeddedServletContainer(TomcatEmbeddedServletContainerFactory.java:179)
	at org.springframework.boot.context.embedded.EmbeddedWebApplicationContext.createEmbeddedServletContainer(EmbeddedWebApplicationContext.java:164)
	at org.springframework.boot.context.embedded.EmbeddedWebApplicationContext.onRefresh(EmbeddedWebApplicationContext.java:134)
	... 8 common frames omitted
Caused by: org.apache.catalina.LifecycleException: Failed to start component [StandardServer[-1]]
	at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:167)
	at org.apache.catalina.startup.Tomcat.start(Tomcat.java:344)
	at org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer.initialize(TomcatEmbeddedServletContainer.java:99)
	... 13 common frames omitted
Caused by: org.apache.catalina.LifecycleException: Failed to start component [StandardService[Tomcat]]
	at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:167)
	at org.apache.catalina.core.StandardServer.startInternal(StandardServer.java:793)
	at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:150)
	... 15 common frames omitted
Caused by: org.apache.catalina.LifecycleException: Failed to start component [StandardEngine[Tomcat]]
	at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:167)
	at org.apache.catalina.core.StandardService.startInternal(StandardService.java:422)
	at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:150)
	... 17 common frames omitted
Caused by: org.apache.catalina.LifecycleException: A child container failed during start
	at org.apache.catalina.core.ContainerBase.startInternal(ContainerBase.java:947)
	at org.apache.catalina.core.StandardEngine.startInternal(StandardEngine.java:262)
	at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:150)
	... 19 common frames omitted

	根源：spirngcloud依赖于spingboot,在pom文件中将顺序颠倒，依赖找不到springboot 中内置的Tomcat容器会报错
	解决：
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <version>${spring.boot.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
            <version>${spring.boot.version}</version>
        </dependency>
        <!--记一下大坑，springcloud依赖于springboot，depengcy放在springboot的后面-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-eureka-server</artifactId>
            <version>${spring.cloud.version}</version>
        </dependency>
    </dependencies>
