package com.example.red_chapter1


import android.util.Log
import kotlinx.coroutines.*

object Sample {

    const val TAG = "Sample"

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


    // launch 와 같은 코루틴 빌더는 코루틴이기에 코루틴은 코루틴 스코프안에서만 시작되므로 코루틴 빌더를 호출하기 위해서는
    // 코루틴 스코프 내에서 호출되어야 한다.
    fun `suspend_함수에서_코루틴_빌더_호출`() = runBlocking {
        doOneTwoThree()
        printLog("launch4: ${Thread.currentThread().name}")
        printLog("5")
    }

    //역시나 코루틴 스코프는 수신객체를 반환하는데 여기에서 coroutineContext 라는 프로퍼티를 가지고 있으며, 이 프로퍼티는 코루틴의 내용이 들어져 있다.
    //일시중단함수 즉 fun 앞에 suspend 가 붙게된 함수를 호출하려면 코루틴 스코프 혹은 또다른 suspend 함수에서 호출해야 한다.
    //옆에 화살표로 되어 있는 표시가 통상적으로 중단점이라고 생각하면 된다. (suspension point)
    suspend fun doOneTwoThree() = coroutineScope {
        launch {
            printLog("launch1: ${Thread.currentThread().name}")
            delay(1000L)  //suspension point
            printLog("3")
        }
        launch {
            printLog("launch2: ${Thread.currentThread().name}")
            printLog("1")
        }
        launch {
            printLog("launch3: ${Thread.currentThread().name}")
            delay(500L) //suspension point
            printLog("2")
        }
        printLog("4")
    }

    //여기서 runBlocking은 현재 쓰레드를 멈추게 만들고, 기다리지만
    //coroutineScope는 현재 스레드를 멈추게 하지 않는다.
    // 호출한 쪽이 suspend되고 시간이 되면 다시 활동하게 된다.


    // 코루틴빌더 launch 는 job 이라는 객체를 반환한다.
    fun `Job을_이용한_제어`() = runBlocking {
        doOneTwoThree1()
        printLog("launch4: ${Thread.currentThread().name}")
        printLog("5")
    }

    suspend fun doOneTwoThree1() = coroutineScope {

        val job = launch {
            printLog("launch1: ${Thread.currentThread().name}")
            delay(1000L)  //suspension point
            printLog("3")
        }

        job.join() // 종료할때까지 기다린다.

        launch {
            printLog("launch2: ${Thread.currentThread().name}")
            printLog("1")
        }
        launch {
            printLog("launch3: ${Thread.currentThread().name}")
            delay(500L) //suspension point
            printLog("2")
        }
        printLog("4")
    }


    //코루틴은 협력적으로 동작하기 때문에 여러 코루틴을 만드는 것이 큰 비용이 들지 않는다.
    // 10만개의 간단한 일을 하는 코루틴도 큰 부담은 아님.
    fun `가벼운_코루틴`() = runBlocking {
        doOneTwoThree2()
        printLog("launch4: ${Thread.currentThread().name}")
        printLog("5")
    }

    suspend fun doOneTwoThree2() = coroutineScope {

        val job = launch {
            printLog("launch1: ${Thread.currentThread().name}")
            delay(1000L)  //suspension point
            printLog("3")
        }

        job.join() // 종료할때까지 기다린다.

        launch {
            printLog("launch2: ${Thread.currentThread().name}")
            printLog("1")
        }

        repeat(1000) {
            launch {
                printLog("launch3: ${Thread.currentThread().name}")
                delay(500L) //suspension point
                printLog("2")
            }
        }
        printLog("4")
    }


    fun `Job에_대해_취소`() = runBlocking {
        doOneTwoThree3()
        printLog("launch4: ${Thread.currentThread().name}")
        printLog("5")
    }

    suspend fun doOneTwoThree3() = coroutineScope {

        val job1 = launch {
            printLog("launch1: ${Thread.currentThread().name}")
            delay(1000L)  //suspension point
            printLog("3")
        }


        val job2 = launch {
            printLog("launch2: ${Thread.currentThread().name}")
            printLog("1")
        }


        val job3 = launch {
            printLog("launch3: ${Thread.currentThread().name}")
            delay(500L) //suspension point
            printLog("2")
        }
        delay(600L)

        job1.cancel()
        job2.cancel()
        job3.cancel()

        printLog("4")
    }


    // job 내에 어떤 루프가 있다면 이건 취소가 불가능하다.
    // 취소가 가능하게 하려면 job1 에서 수신객체로 전달받는 코루틴스코프의 정보를 가지고 있는 coroutineContext 가
    // 활성화 상태인지 여부를 추가해 주면 된다.
    fun `취소_불가능한_Job`() = runBlocking {
        doCount()
    }

    //1,2 하고 바로 Done 이 불리기 위해선 isActive 를 && 로 넣어주면 되고.
    //1~10까지 다 출력하고 나서 done 을 부르고 싶으면 cancelAndJoin 을 해주면된다.
    suspend fun doCount() = coroutineScope {
        val job1 = launch(Dispatchers.Default) {
            var i = 1
            var nextTime = System.currentTimeMillis() + 100L

            while (i <= 10) {
                val currentTime = System.currentTimeMillis()
                if (currentTime >= nextTime) {
                    printLog(i.toString())
                    nextTime = currentTime + 100L
                    i++
                }
            }
        }

        delay(200L)
        job1.cancelAndJoin()
        printLog("doCount Done!")
    }

    //job 의 끝나는 여부를 로그로 취합할 수도 있겠다.
    fun `finally를_사용한_job_종료`() = runBlocking {
        doOneTwoThree4()
        printLog("launch4: ${Thread.currentThread().name}")
        printLog("5")
    }

    suspend fun doOneTwoThree4() = coroutineScope {
        val job1 = launch {
            try {
                printLog("launch1: ${Thread.currentThread().name}")
                delay(1000L)
                printLog("3!")
            } finally {
                printLog("job1 is finishing!")
            }
        }

        val job2 = launch {
            try {
                delay(1000L)
                printLog("launch2: ${Thread.currentThread().name}")
                delay(1000L)
                printLog("1!")
            } finally {
                printLog("job2 is finishing!")
            }
        }

        val job3 = launch {
            try {
                printLog("launch3: ${Thread.currentThread().name}")
                delay(1000L)
                printLog("2!")
            } finally {
                printLog("job3 is finishing!")
            }
        }
        delay(800L)
        job1.cancel()
        job2.cancel()
        job3.cancel()
        printLog("4!")
    }


    //취소 불가능한 블록을 finally 로 넣어도 되긴한다.
    fun `취소_불가능한_블록만들기`() = runBlocking {
        doOneTwoThree5()
    }

    suspend fun doOneTwoThree5() = coroutineScope {
        val job1 = launch {
            withContext(NonCancellable) {
                printLog("launch1: ${Thread.currentThread().name}")
                delay(1000L)
                printLog("3!")
            }
            delay(1000L)
            printLog("job1: end")
        }

        val job2 = launch {
            withContext(NonCancellable) {
                printLog("launch2: ${Thread.currentThread().name}")
                delay(1000L)
                printLog("1!")
            }
            delay(1000L)
            printLog("job2: end")
        }

        val job3 = launch {
            withContext(NonCancellable) {
                printLog("launch3: ${Thread.currentThread().name}")
                delay(1000L)
                printLog("2!")
            }
            delay(1000L)
            printLog("job3: end")
        }

        delay(800L)
        job1.cancel()
        job2.cancel()
        job3.cancel()
        printLog("4!")
    }


    //취소가 되면은 TimeoutCancellationException 발생하여 종료.
    fun `타임_아웃`() = runBlocking {
        withTimeout(500L) {
            doCount1()
        }
    }

    suspend fun doCount1() = coroutineScope {
        val job1 = launch(Dispatchers.Default) {
            var i = 1
            var nextTime = System.currentTimeMillis() + 100L

            while (i <= 10 && isActive) {
                val currentTime = System.currentTimeMillis()
                if (currentTime >= nextTime) {
                    printLog(i.toString())
                    nextTime = currentTime + 100L
                    i++
                }
            }
        }
    }


    // 여기서 500ms 안에 성공했는지 실패했는지 exception 을 발생시키는게 아닌 성공 여부를 확인가능하게끔 바꿔볼 수 있다.
    // 실패했을시에 null 을 반환하는데 아래같이 boolean 형식으로 반환하게 하고 엘비스로 null 일경우 false 로 결과값을 가지게 할 수도 있다.
    fun `withTimeoutOrNull`() = runBlocking {
        val result = withTimeoutOrNull(500) {
            doCount2()
            true
        } ?: false

        printLog(result.toString())
    }

    suspend fun doCount2() = coroutineScope {
        val job1 = launch(Dispatchers.Default) {
            var i = 1
            var nextTime = System.currentTimeMillis() + 100L

            while (i <= 10 && isActive) {
                val currentTime = System.currentTimeMillis()
                if (currentTime >= nextTime) {
                    printLog(i.toString())
                    nextTime = currentTime + 100L
                    i++
                }
            }
        }
    }
}

fun printLog(message: String) {
    Log.d(Sample.TAG, message)
}