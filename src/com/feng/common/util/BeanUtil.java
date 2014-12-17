// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BeanUtil.java

package com.feng.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BeanUtil
{

	private static Log log = LogFactory.getLog(com.feng.common.util.BeanUtil.class);

	public BeanUtil()
	{
	}

	public static Object createObject(String clazzName)
		throws Exception
	{
		try
		{
		return Class.forName(clazzName).newInstance();
		}
		catch(ClassNotFoundException e)
		{
		log.error("������ʧ��!��δ�ҵ�", e);
		throw new Exception("������ʧ��!��δ�ҵ�", e);
		}
	}

	public static Object getProperties(Object obj, String name)
		throws Exception
	{
		Field field = obj.getClass().getDeclaredField(name);
		if (field == null)
		{
			log.error((new StringBuilder("��ȡ����:")).append(obj).append("������:").append(name).append("��ֵ����! �޴�����").toString());
			throw new Exception((new StringBuilder("��ȡ����:")).append(obj).append("������:").append(name).append("��ֵ����! �޴�����").toString());
		}
		Method method = null;
		try
		{
			method = obj.getClass().getMethod((new StringBuilder("get")).append(String.valueOf(name.charAt(0)).toUpperCase()).append(name.substring(1)).toString(), new Class[0]);
		}
		catch (NoSuchMethodException e)
		{
			log.error((new StringBuilder("��ȡ����:")).append(obj).append("������:").append(name).append("��ֵ����! û�д˷���").toString(), e);
			throw e;
		}
		if ("void".equals(method.getReturnType().toString()))
		{
			log.error((new StringBuilder("��ȡ����:")).append(obj).append("������:").append(name).append("��ֵ����! ��������ֵΪ��").toString());
			throw new Exception((new StringBuilder("��ȡ����:")).append(obj).append("������:").append(name).append("��ֵ����! ��������ֵΪ��").toString());
		} else
		{
			return method.invoke(obj, new Object[0]);
		}
	}

	public static void setProperties(Object obj, String name, Object value)
		throws Exception
	{
		Method method = null;
		try
		{
			method = obj.getClass().getMethod((new StringBuilder("set")).append(String.valueOf(name.charAt(0)).toUpperCase()).append(name.substring(1)).toString(), new Class[] {
				value != null ? value.getClass() : obj.getClass().getDeclaredField(name).getType()
			});
		}
		catch (NoSuchMethodException e)
		{
			log.error((new StringBuilder("���ö���:")).append(obj).append("������:").append(name).append("��ֵ����! �޴˷���").toString());
			throw new Exception((new StringBuilder("���ö���:")).append(obj).append("������:").append(name).append("��ֵ����! �޴˷���").toString());
		}
		method.invoke(obj, new Object[] {
			value
		});
	}

	public static void copyPropertys(Object fromobj, Object toobj)
		throws Exception
	{
		try
		{
			Map map = describeNullValue(fromobj);
			java.util.Map.Entry e;
			for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext(); setProperties(toobj, (String)e.getKey(), e.getValue()))
				e = (java.util.Map.Entry)iterator.next();

		}
		catch (Exception e)
		{
			log.error("������1�еĲ�Ϊ�յ����ݿ���������2�쳣", e);
			throw new Exception("������1�еĲ�Ϊ�յ����ݿ���������2�쳣", e);
		}
	}

	public static String getClassName(Object obj)
	{
		String className = obj.getClass().getCanonicalName();
		if (className.indexOf(".") != -1)
			return className.substring(className.lastIndexOf(".") + 1);
		else
			return className;
	}

	public static String getClassName(Class clazz)
	{
		String className = clazz.getName();
		if (className.indexOf(".") != -1)
			return className.substring(className.lastIndexOf(".") + 1);
		else
			return className;
	}

	public static Map describe(Object obj)
		throws Exception
	{
		Map map = new HashMap();
		Field fields[] = obj.getClass().getDeclaredFields();
		Field afield[];
		int j = (afield = fields).length;
		for (int i = 0; i < j; i++)
		{
			Field field = afield[i];
			map.put(field.getName(), getFieldValue(obj, field));
		}

		return map;
	}

	public static Map describeNullValue(Object obj)
		throws Exception
	{
		Map map = new HashMap();
		Field fields[] = obj.getClass().getDeclaredFields();
		Field afield[];
		int j = (afield = fields).length;
		for (int i = 0; i < j; i++)
		{
			Field field = afield[i];
			Object object = getFieldValue(obj, field);
			if (object != null)
				map.put(field.getName(), object);
		}

		return map;
	}

	public static Object invokeMethod(Class clazz, String methodName, Object params[])
		throws Exception
	{
		Object obj = clazz.newInstance();
		return invokeMethod(obj, methodName, params);
	}

	public static Object invokeMethod(Object obj, String methodName, Object params[])
		throws Exception
	{
		try
		{
		Method method;
		method = null;
		if (params == null)
		{
			method = obj.getClass().getMethod(methodName, new Class[0]);
		} else
		{
			Class clazzs[] = new Class[params.length];
			Object aobj[];
			int j = (aobj = params).length;
			for (int i = 0; i < j; i++)
			{
				Object o = aobj[i];
				clazzs[0] = o.getClass();
			}

			method = obj.getClass().getMethod(methodName, clazzs);
		}
		return method.invoke(obj, params);
		}
		catch(Exception e)
		{
		log.error((new StringBuilder("ִ�ж���")).append(obj.getClass()).append("�з���:").append(methodName).append("ʱ����!").toString(), e);
		throw e;
		}
	}

	public static void populate(Object obj, Map map)
		throws Exception
	{
		java.util.Map.Entry e;
		for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext(); setProperties(obj, (String)e.getKey(), e.getValue()))
			e = (java.util.Map.Entry)iterator.next();

	}

	private static Object getFieldValue(Object obj, Field field)
		throws Exception
	{
		Method method = null;
		try
		{
			method = obj.getClass().getMethod((new StringBuilder("get")).append(String.valueOf(field.getName().charAt(0)).toUpperCase()).append(field.getName().substring(1)).toString(), new Class[0]);
		}
		catch (NoSuchMethodException e)
		{
			log.error((new StringBuilder("����:")).append(field.getName()).append("û��get�������޷�����ֵ").toString());
			return null;
		}
		if (method.getReturnType().equals("void"))
			return null;
		else
			return method.invoke(obj, new Object[0]);
	}

}
