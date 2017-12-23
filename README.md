# Spring Data Jpa Helper
> handle DataIntegrityViolationException(cause by uniqueConstraint) for spring-data-jpa user, and also add some useful methods to build REST web service.

At first, I do feel sorry about my awkward english grammar. I'm still work on it. Any contribute about correct my English mistakes will be welcome.

### Getting Started
1. add dependency
    ```groovy
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }

    dependencies {
        compile("com.github.noob9527:spring-data-jpa-helper:0.1.0")
        // ...
    }
    ```

2. write repository like usual
	```java
    @Entity
    public class Vehicle {
        @Id
        @GeneratedValue
        private Integer id;
        @Unique
        private String plateNumber;
        // ...
    }
    public interface VehicleRepository extends
        CrudRepository<Vehicle, Integer>,
        JpaHelperRepository<Vehicle, Integer> {
    }
    ```
3. It's done, Let's check it out
	```java
    @Test
    @DisplayName("save duplicate entity should throw exception with handy error details")
    void friendlyUniqueConstraint() {
        Vehicle vehicle1 = new Vehicle("foo");
        Vehicle vehicle2 = new Vehicle("foo");
        repository.save(vehicle1);
        try {
            repository.save(vehicle2);
            fail("should throw exception");
        } catch (DuplicatedEntityException e) {
            assertThat(e.getFieldMap())
                    .containsKey("plateNumber")
                    .containsValue("foo");
        }
    }

    @Test
    @DisplayName("find an entity by unique key")
    void findByUniqueKey() {
        Vehicle vehicle1 = new Vehicle("foo");
        repository.save(vehicle1);
        Vehicle vehicle2 = new Vehicle("foo");
        Vehicle result = repository.findByUQ(vehicle2);
        assertThat(result).isNotNull();
    }
    ```
WARN: you should know that Application layer validation can't fully replace the DB layer validation, unless you set your transaction isolation level to ** Serializable **, which is not recommended.

### Mainly Annotations
1. `@Unique` to specify single column unique key(see sample above)
2. `@CompositeUnique` to specify mutiple column unique key, It looks like
	```java
    @Entity
    @FriendlyUnique(compositeUniques = {
            @CompositeUnique(fields = {"foo", "bar"})
    })
    public class CompositeExample {
        @Id
        @GeneratedValue
        private Integer id;
        private String foo;
        private String bar;
        // ...
    }
    ```
3. by set enable-jpa-uq-annotation=true, It can recognize Jpa standard annotation @Column(unique=true) as @Unique, @UniqueConstraint as @CompositeUnique.

Each annotation(except jpa annotation) accepts belows property for advance usage.
```kotlin
/**
 * Indicates if jpa-helper will use it in `exists` and `findByUQ` methods
 */
val isIdentity: Boolean = true,
/**
 * Indicates if jpa-helper will do uniqueCheck for you
 */
val isConstraint: Boolean = true,
/**
 * exists query priority
 * consider an entity has two unique key
 * class SomeEntity (@Unique val foo:String?=null, @Unique val bar:String?=null)
 * and db already have two records ("aaa", null), (null, "bbb")
 * then you try to findByUQ(SomeEntity("aaa", "bbb"))
 * this property indicated which entity you will be returned
 * findByUQ will use the larger priority UQ at first
 */
val queryPriority: Int = 0
```

### Configuration
Taking advantage of spring boot autoconfigure. you needn't do anything in general case. but you can still customize belows properties
```
spring.data.jpa.helper.enable-jpa-uq-annotation=false
spring.data.jpa.helper.jpa-annotation-constraint=false
spring.data.jpa.helper.jpa-annotation-identity=false
spring.data.jpa.helper.batch-size=50
```

### Additional methods
Handle unique key for developer is the main purpose of this lib, but it can do more than that, generally it adds belows "ability" to your repository(by extends JpaHelperRepository).
```kotlin
fun <S : T> findByUQ(entity: S): S?

// belows method might be useful for your REST service
// you can map them to HTTP methods(post, put, patch)
fun <S : T> create(entity: S): S
fun <S : T> update(entity: S): S
fun <S : T> patch(entity: S): S

// belows method will do create or update based on your unique key
fun <S : T> createOrUpdate(entity: S): S
fun <S : T> createOrPatch(entity: S): S

// batch operation
fun <S : T> create(entities: Iterable<S>, isAtomic: Boolean = true): Iterable<S>
fun <S : T> update(entities: Iterable<S>, isAtomic: Boolean = true): Iterable<S>
fun <S : T> patch(entities: Iterable<S>, isAtomic: Boolean = true): Iterable<S>
fun <S : T> createOrUpdate(entities: Iterable<S>, isAtomic: Boolean = true): Iterable<S>
fun <S : T> createOrPatch(entities: Iterable<S>, isAtomic: Boolean = true): Iterable<S>

// null safety, you won't need them in spring data 2+(not stable yet)
// these methods will throw EmptyResultDataAccessException(just like spring-data-jpa2+) when result can't be found
fun getById(id: ID): T
fun getOne(spec: Specification<T>): T
```

### Core Concept
1. User/Developer friendly unique constraints
	TBD
2. Find entity by unique constraints(findByUQ)
	TBD
3. Batch operation(see this [article](http://frightanic.com/software-development/jpa-batch-inserts/))