package com.xvela.reactive.common.converter;

import com.xvela.reactive.common.api.BaseVo;
import com.xvela.reactive.common.entity.BaseEntity;
import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.TypeMappingBuilder;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.util.ReflectionUtils.findMethod;
import static org.springframework.util.ReflectionUtils.invokeMethod;

public abstract class BuilderConverter<T extends BaseEntity, V extends BaseVo<V>, B> extends AbstractConverter<T, V> {

    private Class<B> destinationBuilderClass;
    private Class<V> destinationClass;
    private Class<T> sourceClass;
    private DozerBeanMapper mapper;

    public BuilderConverter(DozerBeanMapper mapper, Class<T> source, Class<V> dest, Class<B> destinationBuilderClass) {
        super(mapper, source, dest);
        this.destinationBuilderClass = destinationBuilderClass;
        this.destinationClass = dest;
        this.sourceClass = source;
        this.mapper = mapper;
    }

    @PostConstruct
    public void initMapper() {
        mapper.addMapping(new BeanMappingBuilder() {
            @Override
            protected void configure() {
                TypeMappingBuilder mapping = mapping(sourceClass, destinationBuilderClass);
                Set<String> destFields = getDesiredFields(destinationClass).collect(Collectors.toSet());
                getDesiredFields(sourceClass).filter(destFields::contains)
                        .forEach(field -> mapping.fields(field, field(field).setMethod(field)));
                additionalInitMapper(mapping);
                TypeMappingBuilder voToBuilderMapping = mapping(destinationClass, sourceClass);
                notMappingFields().forEach(voToBuilderMapping::exclude);
            }
        });
    }

    private Stream<String> getDesiredFields(Class<?> clazz) {
        List<String> notMappedFields = notMappingFields();
        return Arrays.stream(clazz.getDeclaredFields())
                .map(Field::getName)
                .filter(n -> !notMappedFields.contains(n) && !n.startsWith("$"));
    }

    protected List<String> notMappingFields() {
        return Collections.emptyList();
    }

    protected void additionalInitMapper(TypeMappingBuilder mapping) {
    }

    @Override
    protected final V convert(T t) {
        B b = mapper.map(t, destinationBuilderClass);
        return invoke(destinationBuilderClass, b, "build");
    }

    @SuppressWarnings("all")
    private static <T, V> V invoke(Class<T> classOfT, T t, String methodName) {
        return (V) invokeMethod(findMethod(classOfT, methodName), t);
    }
}
