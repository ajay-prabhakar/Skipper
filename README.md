# Skipper
A repository which I played around with android MVMM Architecture

## What is MVMM-
**Model** - In the model layer, we write our business logic

**ViewModel**- This layer basically interacts with view and model and provide observable(Live Data in case of my example, you can use any observable) which is observed by the view that is fragment and activity.

**View**- As the name suggests, the view is our fragment and Activity. View observe the view model observable to update its element.

**Difference between MVP and MVVM**
In MVVM, we don't have to pass view reference to the view model to update view from the view model, View model has observable that view observe and we use LiveData observable which is LifeCycle Aware so we don't need to take care of views lifecycle to update the view.
but in case of MVP, we pass view reference to the presenter to update the view, so presenter is bound to view’s lifecycle which can lead to lots of boilerplate code to make lifecycle aware.

**What’s better in MVVM ?**
- No more dozens interfaces
- ViewModel and DataModel still testable using JUnit
- ViewModel no longer coupled to a specific View
- ViewModels can supposedly be used and/or composed in multiple views (imagine a completely different tablet View that reuses     multiple ViewModels that have been used so far independently on some phone Views)


Below you can see how we can implement a simple UserRepository that fetches data from the API and sends it to ViewModel which will prepare it for the View.

**UserListViewModel :** 
```
class UserListViewModel(val userRepository: UserRepository) {

    fun getUsers(): Observable<UsersList> {
        //Prepare the data for your UI, the users list
        //and maybe some additional data needed as well
        return userRepository.getUsers()
            .map { UsersList(it, "Users loaded successfully!") }
    }
}

class UserRepository(val userApi: UserApi) {

    fun getUsers(): Observable<List<User>> {
        return userApi.getUsers()
    }
}

interface UserApi {

    @GET("users")
    fun getUsers(): Observable<List<User>>
}
```


the ViewModel, talks to the Repository to get the data and prepare it for the view
**UserRepository :** the Repository /DataModel / Model , gets the data from various sources (cache, DB, API )
**UserApi:** a simple data source, that takes the users list from the API

We can alter our UserRepository and make it store the retrieved user list from the API, and each time getUsers() gets called we immediately return first the cached users, and after that fresh new data from the API. This is easily achieved with RX’s [mergeWith()](http://reactivex.io/RxJava/2.x/javadoc/) operator.


```
class UserRepository(val userApi: UserApi) {

    var cachedUsers = emptyList<User>()

    fun getUsers(): Observable<List<User>> {
        if (cachedUsers.isEmpty()) {
            //Returning users from API
            return userApi.getUsers()
                    .doOnNext { cachedUsers = it }
        } else {
            //Returning cached users first, and then API users
            return Observable.just(cachedUsers)
                    .mergeWith(userApi.getUsers())
                    .doOnNext { cachedUsers = it }
        }
    }
}

```

(Of course, the UserRepository, UserViewModel will be probably some Singletons, provided by Dagger)

What I also like about this approach (separating the ViewModel from the DataModel/Repository), is that the ViewModel does not know, and does not care where the data comes from. It is also not responsible for caching or storing the data. We were able to introduce caching of the API data without any changes in our ViewModel.


Testing our UserRepository is fairly easy using a TestObserver


```
class UserRepositoryTest {

    lateinit var userRepository: UserRepository
    lateinit var userApi: UserApi

    @Before
    fun setup() {
        userApi = mock()
        userRepository = UserRepository(userApi)
    }

    @Test
    fun test_emptyCache_noDataOnApi_returnsEmptyList() {
        `when`(userApi.getUsers()).thenReturn(Observable.just(emptyList<User>()))

        userRepository.getUsers().test()
                .assertValue { it.isEmpty() }
    }

    @Test
    fun test_emptyCache_hasDataOnApi_returnsApiData() {
        `when`(userApi.getUsers()).thenReturn(Observable.just(listOf(aRandomUser())))

        userRepository.getUsers().test()
                .assertValueCount(1)
                .assertValue { it.size == 1 }
    }

    @Test
    fun test_hasCacheData_hasApiData_returnsBothData() {
        val cachedData = listOf(aRandomUser())
        val apiData = listOf(aRandomUser(), aRandomUser())
        `when`(userApi.getUsers()).thenReturn(Observable.just(apiData))
        userRepository.cachedUsers = cachedData

        userRepository.getUsers().test()
                //Both cached & API data delivered
                .assertValueCount(2)
                //First cache data delivered
                .assertValueAt(0, { it == cachedData })
                //Secondly api data delivered
                .assertValueAt(1, { it == apiData })
    }

    @Test
    fun test_cache_updatedWithApiData() {
        val apiData = listOf(aRandomUser(), aRandomUser())
        `when`(userApi.getUsers()).thenReturn(Observable.just(apiData))

        userRepository.getUsers().test()

        assertEquals(userRepository.cachedUsers, apiData)
    }

    fun aRandomUser() = User("mail@test.com", "John", UUID.randomUUID().toString().take(5))
}

```
#### Android Room

In Android, data is represented in data classes, and the data is accessed and modified using function calls. However, in the database world, you need entities and queries.

An **entity** represents an object or concept, and its properties, to store in the database. An entity class defines a table, and each instance of that class represents a row in the table. Each property defines a column. In your app, the entity is going to hold information about a night of sleep.

A **query** is a request for data or information from a database table or combination of tables, or a request to perform an action on the data. Common queries are for getting, inserting, and updating entities. For example, you could query for all the sleep nights on record, sorted by start time.

Room does all the hard work for you to get from Kotlin data classes to entities that can be stored in SQLite tables, and from function declarations to SQL queries.

You must define each entity as an annotated data class, and the interactions as an annotated interface, a data access object (DAO). Room uses these annotated classes to create tables in the database, and queries that act on the database.
