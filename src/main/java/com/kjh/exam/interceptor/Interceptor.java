package com.kjh.exam.interceptor;

import com.kjh.exam.container.ContainerComponent;
import com.kjh.exam.http.Rq;

public abstract class Interceptor implements ContainerComponent {
	abstract public boolean runBeforeAction(Rq rq);
}
