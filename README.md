# MySeckill
高并发秒杀类场景模拟

本次项目使用Myelipse进行开发，开发流程全记录到了我的博客：博文地址如下
前言与简介：http://www.cnblogs.com/ygj0930/p/6905639.html 
           主要是项目用到的技术以及项目需求分析等介绍。

依赖管理与数据库设计：http://www.cnblogs.com/ygj0930/p/6905787.html
           整合SSM框架所用到的依赖、数据库设计脚本的编写。
           
DAO层开发：http://www.cnblogs.com/ygj0930/p/6905871.html
          DAO层实体类的编写、Mybatis接口编写、xml文件编写、整合Spring与Mybatis。
           
Service层开发：http://www.cnblogs.com/ygj0930/p/6930558.html
          Service层采用“面向接口开发”原则，包括接口设计、实现类定义、Spring托管service实现类、基于注解的事务管理。
          
Web层开发：http://www.cnblogs.com/ygj0930/p/6937563.html
          主要是SpringMVC的controller开发，采用了RestFul架构的url映射规则。
          
高并发优化记录：http://www.cnblogs.com/ygj0930/p/6937906.html
          简述了大型高并发分布式项目的优化与架构思路、并对本次项目的两个高并发优化点进行优化——采用redis缓存、mysql存储过程。
