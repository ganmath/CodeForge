
@echo off
set MAVEN_OPTS=%MAVEN_OPTS% -Xmx1024m
if exist "%~dp0\.mvn\wrapper\maven-wrapper.jar" (
  set WRAPPER_JAR="%~dp0\.mvn\wrapper\maven-wrapper.jar"
) else (
  set WRAPPER_JAR=
)
if defined WRAPPER_JAR (
  java -Dmaven.multiModuleProjectDirectory="%~dp0" -cp %WRAPPER_JAR% org.apache.maven.wrapper.MavenWrapperMain %*
) else (
  mvn %*
)
