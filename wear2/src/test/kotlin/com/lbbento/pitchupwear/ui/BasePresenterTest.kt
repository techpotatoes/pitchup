package com.lbbento.pitchupwear.ui

import com.lbbento.pitchuptuner.service.TunerResult
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test
import rx.Observable
import rx.subscriptions.CompositeSubscription

class TestBasePresenter(override val subscriptions: CompositeSubscription) : BasePresenter<BaseView>() {

    fun testSubscriber() {
        val obs: Observable<TunerResult> = Observable.create({})
        obs.subscribeAndManage {}
    }
}

class BasePresenterTest {

    val mockSubscription: CompositeSubscription = mock()
    val presenter = TestBasePresenter(mockSubscription)

    @Test
    fun shouldUnsubscribeOnStop() {
        presenter.onStop()

        verify(mockSubscription).unsubscribe()
    }

    @Test
    fun shouldSubscribeAndManageSubscription() {
        presenter.testSubscriber()

        verify(mockSubscription).add(any())
    }

}