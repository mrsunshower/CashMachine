package com.cashmachine.command;

import com.cashmachine.exception.InterruptOperationException;

/**
 * Created by KenTerror on 28.12.2016.
 */
interface Command {
    void execute() throws InterruptOperationException;
}
