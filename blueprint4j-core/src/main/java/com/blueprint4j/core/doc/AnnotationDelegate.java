package com.blueprint4j.core.doc;

import com.blueprint4j.core.annotation.Diagram;
import com.blueprint4j.core.annotation.Doc;
import com.blueprint4j.core.usecase.Step;
import com.blueprint4j.core.usecase.UseCase;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AnnotationDelegate {

    public AnnotationDelegate() {
    }

    public String getNameForDocAnnotatedField(Field field) {
        Annotation annotation = field.getDeclaredAnnotation(Doc.class);
        if (annotation == null) {
            String name = field.getName();
            return name;
        }
        Doc docAnnotation = (Doc) annotation;
        String name = docAnnotation.name();
        if (name == null || name.isEmpty()) {
            return field.getName();
        }
        return docAnnotation.name();
    }

    public String getNameForDocAnnotatedClass(Class mainClazz) {
        Annotation annotation = mainClazz.getDeclaredAnnotation(Doc.class);
        if (annotation == null) {
            String name = mainClazz.getSimpleName();
            return name;
        }
        Doc docAnnotation = (Doc) annotation;
        String name = docAnnotation.name();
        if (name == null || name.isEmpty()) {
            return mainClazz.getSimpleName();
        }
        return docAnnotation.name();
    }

    public String getNameForDiagramAnnotatedField(Field field) {
        Annotation annotation = field.getDeclaredAnnotation(Diagram.class);
        if (annotation == null) {
            String name = field.getName();
            return name;
        }
        Diagram diagramAnnotation = (Diagram) annotation;
        String name = diagramAnnotation.name();
        if (name == null || name.isEmpty()) {
            return field.getName();
        }
        return diagramAnnotation.name();
    }

    public String getNameForDiagramAnnotatedClass(Class mainClazz) {
        Annotation annotation = mainClazz.getDeclaredAnnotation(Diagram.class);
        if (annotation == null) {
            String name = mainClazz.getSimpleName();
            return name;
        }
        Diagram diagramAnnotation = (Diagram) annotation;
        String name = diagramAnnotation.name();
        if (name == null || name.isEmpty()) {
            return mainClazz.getSimpleName();
        }
        return diagramAnnotation.name();
    }

    public String getDescriptionForField(Field field) {
        if (field.getType().isAnnotationPresent(Doc.class)) {
            return field.getType().getDeclaredAnnotation(Doc.class).description();
        }
        return "No description available";
    }

    public String getDescriptionForDocAnnotatedClass(Class clazz) {
        String defaultDescription = "No description available";
        Annotation annotation = clazz.getDeclaredAnnotation(Doc.class);
        if (annotation == null) {
            return defaultDescription;
        }
        Doc docAnnotation = (Doc) annotation;
        String description = docAnnotation.description();
        if (description == null || description.isEmpty()) {
            return defaultDescription;
        }
        return description;
    }

    public Class getUseCaseClassForDiagramAnnotatedField(Field field) {
        Annotation annotation = field.getDeclaredAnnotation(Diagram.class);
        if (annotation == null) {
            return null;
        }
        Diagram diagramAnnotation = (Diagram) annotation;
        Class useCaseClass = diagramAnnotation.useCase();
        return useCaseClass;
    }

    public List<Step> getStepsFromUseCaseClass(Class useCaseClass) {
        try {
            Object object = useCaseClass.newInstance();
            Method useCaseMethod = useCaseClass.getMethod("useCase");
            return (List<Step>) useCaseMethod.invoke(object);
        } catch (NoSuchMethodException e) {
            return new ArrayList<Step>();
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}