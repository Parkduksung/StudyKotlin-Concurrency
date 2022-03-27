package com.example.red_chapter1

import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

object Sample {

    private const val TAG = "Sample"

    fun `간단한_코루틴`() = runBlocking {
        Log.d(TAG, Thread.currentThread().name)
        Log.d(TAG, "Hello!")
    }


    //코루틴을 쓰이는 모든곳에는 코루틴 스코프가 있다  => 코루틴의 시작은 코루틴 스코프.
    fun `코루틴_빌더의_수신객체`() = runBlocking {
        Log.d(TAG, this.toString()) // 수신객체로 CoroutineScope 의 자식인 BlockingCoroutine 이 호출된다.
        Log.d(TAG, Thread.currentThread().name)
        Log.d(TAG, "Hello!")
    }


    //코루틴 스코프는 코루틴을 제대로 처리하기 위한 정보, 코루틴 컨텍스트를 가지고 있다.
    fun `코루틴_컨텍스트`() = runBlocking {
        Log.d(TAG, coroutineContext.toString()) //코루틴 객체는 코루틴 컨텍스트라는 프로퍼티를 가지고 있다.
        Log.d(TAG, Thread.currentThread().name)
        Log.d(TAG, "Hello!")
    }

    //launch = 코루틴 빌더 => 새로운 코루틴을 만듦 => 새로운 코루틴 스코프를 만듦
    //launch 는 할 수 있다면 다른 코루틴 코드를 같이 수행 하자는 의미의 코루틴 빌더입니다.
    fun `launch_코루틴_빌더`() = runBlocking {
        launch {
            Log.d(TAG, "launch: ${Thread.currentThread().name}")
            Log.d(TAG, "World!")
        }
        Log.d(TAG, "runBlocking: ${Thread.currentThread().name}")
        Log.d(TAG, "Hello!")
    }
    //해석
    //runBlocking ,launch 둘다 메인 스레드를 사용하는데 runBlocking 의 코드들이 메인 스레드를 다 사용할 때가지
    //launch 코드 블록이 기다리는 것이다.


    fun `delay_함수`() = runBlocking {
        launch {
            Log.d(TAG, "launch: ${Thread.currentThread().name}")
            Log.d(TAG, "World!")
        }
        Log.d(TAG, "runBlocking: ${Thread.currentThread().name}")
        delay(500L)  // Suspension Point (중단점)
        Log.d(TAG, "Hello!")
    }
    //delay 가 호출되면 runBlocking 의 코루틴이 잠에들고 launch 의 작업이 실행되며 0.5초 이후에 잠이 깬 runBlocking 이 실행된다.
    // 잠이들게 되면 메인스레드를 launch 에 양보해주기 때문에 launch 작업이 실행되는 것이다.


    fun `코루틴_내에서_sleep`() = runBlocking {
        launch {
            Log.d(TAG, "launch: ${Thread.currentThread().name}")
            Log.d(TAG, "World!")
        }
        Log.d(TAG, "runBlocking: ${Thread.currentThread().name}")
        Thread.sleep(500)
        Log.d(TAG, "Hello")
    }
    //Thread.sleep 을 하게되면 코루틴 내에서 메인스레드를 양보하는게 아닌 코루틴이 아무 일을 하지 않는 동안에도
    //스레드르 양보하지 않고 독점한다.


    //순서 예측해보기.
    fun `한번에_여러_launch`() = runBlocking {
        launch {
            Log.d(TAG, "launch1: ${Thread.currentThread().name}")
            delay(1000L)
            Log.d(TAG, "3!")
        }
        launch {
            Log.d(TAG, "launch2: ${Thread.currentThread().name}")
            Log.d(TAG, "1!")
        }
        Log.d(TAG, "runBlocking: ${Thread.currentThread().name}")
        delay(500L)
        Log.d(TAG, "2!")
    }

    // runBlocking 이 다끝나야지만 블럭 밖에 있는 로그가 찍히게 된다.
    // 이런 특성 때문에 계층적, 구조적이라고도 한다.
    fun `상위_코루틴은_하위_코루틴을_끝까지_책임진다`() {
        runBlocking {
            launch {
                Log.d(TAG, "launch1: ${Thread.currentThread().name}")
                delay(1000L)
                Log.d(TAG, "3!")
            }
            launch {
                Log.d(TAG, "launch2: ${Thread.currentThread().name}")
                Log.d(TAG, "1!")
            }
            Log.d(TAG, "runBlocking: ${Thread.currentThread().name}")
            delay(500L)
            Log.d(TAG, "2!")
        }
        Log.d(TAG, "4!")
    }

    //delay, launch 등 지금까지 봤던 함수들은 코루틴 내에서만 호출 할 수 있다.
    // 그렇지만 코드를 함수로 분리하고 싶을 때가 있는데 이럴 경우에는 fun 앞에 suspend 라고 붙여주면 된다.
    fun `suspend_함수`() = runBlocking {
        launch {
            doThree()
        }
        launch {
            doOne()
        }
        doTwo()
    }

    suspend fun doThree() {
        Log.d(TAG, "launch1: ${Thread.currentThread().name}")
        delay(1000L)
        Log.d(TAG, "3!")
    }

    // ddOne 같은 경우에는 delay 가 사용되지 않아서 굳이 suspend 라는 키워드를 붙일 필요는 없다.
    suspend fun doOne() {
        Log.d(TAG, "launch1: ${Thread.currentThread().name}")
        Log.d(TAG, "1!")
    }

    suspend fun doTwo() {
        Log.d(TAG, "runBlocking: ${Thread.currentThread().name}")
        delay(500L)
        Log.d(TAG, "2!")
    }

    // 만약에 suspend 함수를 다른 함수에서 호출하려면 그 함수가 suspend 함수이거나 코루틴 빌더를 통해 코루틴을 만들어야 한다.


}