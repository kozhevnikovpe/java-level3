package Lesson7;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Tester {

    /**
     * Вовращает все методы с указанной аннотацией. Если методов больше заданного числа, вызывает исключение
     * @param methods
     * @param annotationClass
     * @param max
     * @return
     */
    private static List<Method> getMethodsWithAnnotation(Method[] methods, Class annotationClass, int max){
        List<Method> result = new ArrayList<>();
        for(Method method: methods){
            if(method.getAnnotation(annotationClass)!=null)
                result.add(method);
        }
        if(max>0 && result.size()>max){
            throw new RuntimeException("Метод с аннотацией "+annotationClass.getSimpleName()+" встречается более "+max+" раз");
        }else
            return result;
    }
    private static List<Method> getMethodsWithAnnotation(Method[] methods, Class annotationClass){
        return getMethodsWithAnnotation(methods,annotationClass,0);
    }

    /**
     * Возвращает единственный метод с аннотацией
     * @param methods список методов
     * @param annotationClass класс аннотации
     * @return Method
     */
    private static Method getMethodWithOneAnnotation(Method[] methods, Class annotationClass){
        List<Method> result = getMethodsWithAnnotation(methods,annotationClass,1);

        if(result.size()==1)
            return result.get(0);
        else if(result.size()==0)
            return null;
        else
            throw new RuntimeException("Метод с аннотацией "+annotationClass.getSimpleName()+" встречается более 1 раза");
    }

    public static void start(Class testClass){
        Method[] methods = testClass.getMethods();
        Method before = getMethodWithOneAnnotation(methods,BeforeSuite.class);
        Method after = getMethodWithOneAnnotation(methods,AfterSuite.class);
        List<Method> tests = getMethodsWithAnnotation(methods,Test.class);
        tests.sort((o1, o2) -> {
            Test a1 = o1.getAnnotation(Test.class);
            Test a2 = o2.getAnnotation(Test.class);
            return a1.value()-a2.value();
        });

        try {
            Object testObjects = testClass.newInstance();
            if(before!=null)
                before.invoke(testObjects);
            for(Method test: tests){
                test.invoke(testObjects);
            }
            if(after!=null)
                after.invoke(testObjects);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
