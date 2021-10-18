package com.sunday.shiftsapplication.service

import co.infinum.retromock.meta.Mock
import co.infinum.retromock.meta.MockResponse
import retrofit2.http.GET

interface ShiftService {
    @Mock
    @MockResponse(body = shifts)
    @GET("shifts")
    suspend fun getShifts(): ApiResponse
}

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
    	 { 
    		"role": "Prep",
    		"name": "Anton",
    		"start_date": "2018-04-20T09:00:00-0800",
    		"end_date": "2018-04-20T12:00:00-0800",
    		"color": "blue"
    	 },
    	 { 
    		"role": "Front of House",
    		"name": "Eugene",
    		"start_date": "2018-04-20T12:00:00-0800",
    		"end_date": "2018-04-20T22:00:00-0800",
    		"color": "red"
    	 },
    	 { 
    		"role": "Cook",
    		"name": "Keyvan",
    		"start_date": "2018-04-21T07:00:00-0800",
    		"end_date": "2018-04-21T12:00:00-0800",
    		"color": "green"
    	 },	
    	  { 
    		"role": "Waiter",
    		"name": "Anna",
    		"start_date": "2018-04-21T09:00:00-0800",
    		"end_date": "2018-04-21T14:00:00-0800",
    		"color": "red"
    	 },
    	  { 
    		"role": "Prep",
    		"name": "Anton",
    		"start_date": "2018-04-21T07:00:00-0800",
    		"end_date": "2018-04-21T12:00:00-0800",
    		"color": "blue"
    	 },
    	 { 
    		"role":"Front of House",
    		"name": "Eugene",
    		"start_date": "2018-04-21T12:00:00-0800",
    		"end_date": "2018-04-21T22:00:00-0800",
    		"color": "red"
    	 },	
    	 { 
    		"role":"Cook",
    		"name": "Keyvan",
    		"start_date": "2018-04-22T07:00:00-0800",
    		"end_date": "2018-04-22T12:00:00-0800",
    		"color": "green"
    	 },
    	 { 
    		"role": "Waiter",
    		"name": "Anna",
    		"start_date": "2018-04-22T09:00:00-0800",
    		"end_date": "2018-04-22T14:00:00-0800",
    		"color": "red"
    	 },	
    	  { 
    		"role":"Prep",
    		"name": "Anton",
    		"start_date": "2018-04-22T07:00:00-0800",
    		"end_date": "2018-04-22T12:00:00-0800",
    		"color": "blue"
    	 },
    	 { 
    		"role": "Front of House",
    		"name": "Eugene",
    		"start_date": "2018-04-22T12:00:00-0800",
    		"end_date": "2018-04-22T22:00:00-0800",
    		"color": "red"
    	 }	
    	]
    }
"""