REM SET JAVA_HOME=D:\SDK\jdk6_x64
SET ZBUS_HOME=..
SET JAVA_OPTS=-Dfile.encoding=UTF-8 -server -Xms64m -Xmx1024m -XX:+UseParallelGC
SET MAIN_CLASS=org.zstacks.zbus.server.ZbusServer
REM -p 15555 -store dummy|sql
SET MAIN_OPTS=-p 15555 -store dummy -track 127.0.0.1:16666;127.0.0.1:16667 -verbose true
SET LIB_OPTS=%ZBUS_HOME%/lib;%ZBUS_HOME%/lib/*;%ZBUS_HOME%/conf;
"%JAVA_HOME%\bin\java" %JAVA_OPTS% -cp %LIB_OPTS% %MAIN_CLASS% %MAIN_OPTS% 