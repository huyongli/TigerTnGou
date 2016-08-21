package cn.ittiger.database.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 主键配置，必须配置该注解，不配置columnName则默认以字段名作为列名，autoGenerate表示主键是否为自增长，默认为是
 * @author: huylee
 * @time:	2015-8-13下午10:27:13
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PrimaryKey {
	/**
	 * 配置该字段映射到数据库中的列名，不配置默认为字段名
	 * @author: huylee
	 * @time:	2015-8-13下午10:33:14
	 * @return
	 */
	public String columnName() default "";
	/**
	 * 该主键是否设置为自增长，默认为否
	 * Author: hyl
	 * Time: 2015-8-14下午4:54:10
	 * @return
	 */
	public boolean isAutoGenerate() default false;
}
