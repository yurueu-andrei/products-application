package ru.clevertec.cheque.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import ru.clevertec.cheque.annotation.MyCacheable;
import ru.clevertec.cheque.cache.Cache;
import ru.clevertec.cheque.cache.config.CacheConfig;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

@Aspect
@Component
@ConditionalOnBean(CacheConfig.class)
@RequiredArgsConstructor
public class CachingAspect {
    private boolean findAllWasInvoked = false;
    private final Cache cache;

    @Pointcut("execution(public !void ru.clevertec.cheque.repository.*.findById(..))")
    public void findByIdRepositoryMethod() {
    }

    @Pointcut("execution(public !void org.springframework.data.repository.Repository+.findAll(..))")
    public void findAllRepositoryMethod() {
    }

    @Pointcut("execution(public !void ru.clevertec.cheque.repository.*.save(..))")
    public void saveRepositoryMethod() {
    }

    @Pointcut("execution(public void ru.clevertec.cheque.repository.*.deleteById(..))")
    public void deleteRepositoryMethod() {
    }

    @Around("findByIdRepositoryMethod()")
    public Object cachingFindByIdResults(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        boolean hasAnnotation = checkAnnotation(proceedingJoinPoint);

        if (hasAnnotation) {
            Long argument = (Long) proceedingJoinPoint.getArgs()[0];
            Object cachedObject = cache.get(argument);
            if (cachedObject != null) {
                System.out.println(cache);
                return Optional.of(cachedObject);
            } else {
                Optional<?> retVal = (Optional<?>) proceedingJoinPoint.proceed();
                retVal.ifPresent(value -> cache.set(argument, value));
                System.out.println(cache);
                return retVal;
            }
        }
        return proceedingJoinPoint.proceed();
    }

    @Around("findAllRepositoryMethod()")
    public Object cachingFindAllResults(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        boolean hasAnnotation = checkAnnotation(proceedingJoinPoint);

        if (hasAnnotation) {
            if (findAllWasInvoked) {
                return cache.getAll();
            } else {
                List<?> retVal = (List<?>) proceedingJoinPoint.proceed();
                findAllWasInvoked = true;
                retVal.forEach(item -> {
                    try {
                        cache.set(findEntityId(item), item);
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                });
            }
        }
        return proceedingJoinPoint.proceed();
    }

    @Around("saveRepositoryMethod()")
    public Object cachingSaveResults(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        boolean hasAnnotation = checkAnnotation(proceedingJoinPoint);

        if (hasAnnotation) {
            Object retVal = proceedingJoinPoint.proceed();
            Long entityId = findEntityId(retVal);
            cache.set(entityId, retVal);
        }
        System.out.println(cache);

        return proceedingJoinPoint.proceed();
    }

    @Around("deleteRepositoryMethod()")
    public void cachingDeleteResults(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        boolean hasAnnotation = checkAnnotation(proceedingJoinPoint);

        if (hasAnnotation) {
            Long id = (Long) proceedingJoinPoint.getArgs()[0];
            if (id == null) {
                proceedingJoinPoint.proceed();
            }
            if (cache.get(id) != null) {
                proceedingJoinPoint.proceed();
                cache.delete(id);
            }
            System.out.println(cache);
        }
    }

    private boolean checkAnnotation(ProceedingJoinPoint proceedingJoinPoint) {
        for (Class<?> i : proceedingJoinPoint.getTarget().getClass().getInterfaces()) {
            if (i.getAnnotation(MyCacheable.class) != null) {
                return true;
            }
        }
        return false;
    }

    private Long findEntityId(Object object) throws Throwable {
        Long id = null;
        for (Field field : object.getClass().getDeclaredFields()) {
            if ("id".equals(field.getName())) {
                field.setAccessible(true);
                id = (Long) field.get(object);
                field.setAccessible(false);
            }
        }
        return id;
    }
}
