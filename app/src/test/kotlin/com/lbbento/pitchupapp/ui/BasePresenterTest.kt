package com.lbbento.pitchupapp.ui

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test
import rx.subscriptions.CompositeSubscription

class TestBasePresenter(override val subscriptions: CompositeSubscription) : BasePresenter<BaseView>()

class BasePresenterTest {

    val mockSubscription: CompositeSubscription = mock()
    val presenter = TestBasePresenter(mockSubscription)

    @Test
    fun shouldUnsubscribeOnDestroy() {
        presenter.onDestroy()

        verify(mockSubscription).unsubscribe()
    }
}