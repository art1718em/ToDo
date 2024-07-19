package com.example.todo.data.network

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.todo.domain.interactor.TodoItemsInteractor
import javax.inject.Inject

class UpdateWorker(
    context: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams) {

    @Inject
    lateinit var interactor: TodoItemsInteractor

    override suspend fun doWork(): Result {
        interactor.getTodoItems()
        return Result.success()
    }
}

