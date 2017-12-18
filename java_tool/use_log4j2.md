## pom.xml
```
                <dependency>
			<groupId>org.slf4j</groupId>
			<artifactId>slf4j-api</artifactId>
			<version>1.7.25</version>
		</dependency>
		<!-- 代码直接调用common-logging会被桥接到slf4j -->
		<dependency>
			<groupId>org.slf4j</groupId>
			<artifactId>jcl-over-slf4j</artifactId>
			<version>1.7.25</version>
			<scope>runtime</scope>
		</dependency>
		<!-- 代码直接调用java.util.logging会被桥接到slf4j -->
		<dependency>
			<groupId>org.slf4j</groupId>
			<artifactId>jul-to-slf4j</artifactId>
			<version>1.7.25</version>
			<scope>runtime</scope>
		</dependency>
		
		<!--核心log4j2jar包 -->
		<dependency>
			<groupId>org.apache.logging.log4j</groupId>
			<artifactId>log4j-api</artifactId>
			<version>2.4.1</version>
		</dependency>
		<dependency>
			<groupId>org.apache.logging.log4j</groupId>
			<artifactId>log4j-core</artifactId>
			<version>2.4.1</version>
		</dependency>
		<!--用于与slf4j保持桥接 -->
		<dependency>
			<groupId>org.apache.logging.log4j</groupId>
			<artifactId>log4j-slf4j-impl</artifactId>
			<version>2.4.1</version>
		</dependency>
		<!--web工程需要包含log4j-web，非web工程不需要 -->
		<dependency>
			<groupId>org.apache.logging.log4j</groupId>
			<artifactId>log4j-web</artifactId>
			<version>2.4.1</version>
			<scope>runtime</scope>
		</dependency>

		<!--需要使用log4j2的AsyncLogger需要包含disruptor -->
		<dependency>
			<groupId>com.lmax</groupId>
			<artifactId>disruptor</artifactId>
			<version>3.2.0</version>
		</dependency>
```
## log4j2.xml
```
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="off" monitorInterval="1800">
	<properties>
		<property name="log_home">logs</property>
		<property name="log_pattern">%d [%t] %-5p %C:%M:%L - %m%n</property>
		<property name="info_log_file_name">info</property>
		<property name="error_log_file_name">error</property>
		<property name="self_log_file_name">self</property>
	</properties>
	<Appenders>
		<Console name="Console" target="SYSTEM_OUT">
			<PatternLayout pattern="${log_pattern}" />
		</Console>
		<RollingRandomAccessFile name="info-log"
			fileName="${log_home}/${info_log_file_name}.log" filePattern="${log_home}/${info_log_file_name}.log.%d{yyyy-MM-dd}">
			<PatternLayout pattern="${log_pattern}" />
			<Policies>
				<TimeBasedTriggeringPolicy />
			</Policies>
			<Filters>
				<ThresholdFilter level="warn" onMatch="DENY"
					onMismatch="NEUTRAL" />
				<ThresholdFilter level="debug" onMatch="ACCEPT"
					onMismatch="DENY" />
			</Filters>
		</RollingRandomAccessFile>
		<RollingRandomAccessFile name="error-log"
			fileName="${log_home}/${error_log_file_name}.log" filePattern="${log_home}/${error_log_file_name}.log.%d{yyyy-MM-dd}">
			<PatternLayout pattern="${log_pattern}" />
			<Policies>
				<TimeBasedTriggeringPolicy />
			</Policies>
			<Filters>
				<ThresholdFilter level="fatal" onMatch="DENY" onMismatch="NEUTRAL" />
                <ThresholdFilter level="warn" onMatch="ACCEPT" onMismatch="DENY" />
			</Filters>
		</RollingRandomAccessFile>
		<RollingRandomAccessFile name="self-log"
			fileName="${log_home}/${self_log_file_name}.log" filePattern="${log_home}/${self_log_file_name}.log.%d{yyyy-MM-dd}">
			<PatternLayout pattern="${log_pattern}" />
			<Policies>
				<TimeBasedTriggeringPolicy />
			</Policies>
		</RollingRandomAccessFile>
	</Appenders>

	<Loggers>
		<logger name="xyz.nesting" level="debug">
			<appender-ref ref="info-log" />
			<appender-ref ref="error-log" />
		</logger>
		<logger name="selflog" level="debug">
			<appender-ref ref="self-log" />
		</logger>
		<root level="info" includeLocation="true">
			<appender-ref ref="Console" />
		</root>
	</Loggers>
</Configuration>
```
