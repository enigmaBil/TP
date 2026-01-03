package com.enigma.tp_api_rest.core.usecases.interactors;

import com.enigma.tp_api_rest.core.domain.task.CreateTask;

public interface TaskInteractor {
    CreateTask createTask(CreateTask createTask);
}
