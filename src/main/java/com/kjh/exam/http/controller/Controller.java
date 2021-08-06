package com.kjh.exam.http.controller;

import com.kjh.exam.container.ContainerComponent;
import com.kjh.exam.http.Rq;

public abstract class Controller implements ContainerComponent {
	public abstract void performAction(Rq rq);
}
