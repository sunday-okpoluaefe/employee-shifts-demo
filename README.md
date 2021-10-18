# Employee Coffee Shift
This is a simple demo to show employee shifts.

It consists of two screens to display all employee shifts and a second screen to create a new shift

## Description
At launch, the app displays employee shifts in a vertical list. A button at the top right to create a new shift.

### Shifts Screen

![shifts_](https://user-images.githubusercontent.com/63934292/137728780-6c7c152a-b1b9-4c03-9815-7b9e2732c148.png)

### Create Shift Screen

![create_shift](https://user-images.githubusercontent.com/63934292/137729309-c809f624-d30b-444c-bbe3-fed156623117.png)


### Create Shift Screen with form validated

![new_shift_validated](https://user-images.githubusercontent.com/63934292/137729187-1e7ee02d-d90c-47a6-a42a-77aa1bd6a049.png)

## Implementation

### Architecture

This project was built using the MVVM architecture. MVVM architecture is a Model-View-ViewModel architecture that removes the tight coupling between each component. Most importantly, in this architecture, the children don't have the direct reference to the parent, they only have the reference by observables.

![Screen Shot 2021-06-25 at 8 31 07 AM](https://user-images.githubusercontent.com/63934292/123387471-b8a82900-d58f-11eb-80b0-10d726cd5dae.png)

#### Project  Structure

The project is built with the single activity, multiple fragment structure.

There is a single activity and two fragments - Shifts and Create Shift Fragments respectively.

A single shared ViewModel to facitilate data flow whose lifecycle is scoped to the main activity.

```

import androidx.lifecycle.*
import com.sunday.shiftsapplication.data.model.Shift
import com.sunday.shiftsapplication.data.repository.Repository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.util.*

class ShiftViewModel constructor (private val repository: Repository): ViewModel(){

    private var _shifts = MutableLiveData<List<Shift>>()
    val shifts: LiveData<List<Shift>> = _shifts

    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _startDate = MutableStateFlow("")
    private val _endDate = MutableStateFlow("")
    private val _employee = MutableStateFlow("")
    private val _role = MutableStateFlow("")
    private val _color = MutableStateFlow("")

    fun setStartDate(date: String) {
        _startDate.value = date
    }

    fun setEndDate(date: String) {
        _endDate.value = date
    }

    fun setEmployee(employee: String) {
        _employee.value = employee
    }

    fun setRole(role: String) {
        _role.value = role
    }

    fun setColor(color: String) {
        _color.value = color
    }

    init {
        loadShifts()
    }

    fun createShift() = viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->

    }) {
        val shift = Shift(
            name = _employee.value,
            startDate = Date(_startDate.value),
            endDate = Date(_endDate.value),
            role = _role.value,
            color = _color.value
        )
        repository.saveShift(shift)
        loadShifts()
    }

    fun loadShifts() = viewModelScope.launch {
        _loading.value = true
        val data: List<Shift> = repository.getShifts()
        data.sortedByDescending {
            it.endDate
        }
        _shifts.value = data
        _loading.value = false
    }

    val isSubmitEnabled: Flow<Boolean> = combine(_startDate, _endDate, _employee, _role, _color) { startDate, endDate, employee, role, color ->
        return@combine startDate.isNotEmpty() and endDate.isNotEmpty() and employee.isNotEmpty() and role.isNotEmpty() and color.isNotEmpty()
    }
}

```

### The Repository

```

import com.sunday.shiftsapplication.data.model.Shift
import com.sunday.shiftsapplication.service.ShiftService
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ShiftRepository @Inject constructor(private val service: ShiftService): Repository {
    private var shifts: MutableList<Shift> = mutableListOf()

    override suspend fun getShifts(): List<Shift> {
      return withContext(IO){
            if(shifts.isNotEmpty()) shifts
            else {
                shifts = service.getShifts().shifts.toMutableList()
                shifts
            }
        }
    }

    override suspend fun saveShift(shift: Shift) {
        withContext(IO){
            shifts.add(shift)
        }
    }
}

```

#### The Network Layer
[Retrofit](https://square.github.io/retrofit/) is used to make network requests. However no network calls was made, as the data response is mocked using [Retromock Library](https://github.com/infinum/Retromock).

```

interface ShiftService {
    @Mock
    @MockResponse(body = shifts)
    @GET("shifts")
    suspend fun getShifts(): ApiResponse
}

```

```

private const val shifts = """
    {
    	"shifts": [
    	 { 
    		"role": "Waiter",
    		"name": "Anna",
    		"start_date": "2018-04-20T09:00:00-0800",
    		"end_date": "2018-04-20T12:00:00-0800",
    		"color": "red"
    	 },
       
       ...
       
```



