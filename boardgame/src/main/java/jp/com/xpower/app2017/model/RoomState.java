package jp.com.xpower.app2017.model;

import org.springframework.stereotype.Controller;
@Controller
public final class RoomState {
	public static class State{
		public static final int WAIT = 0;
        public static final int MIDST = 1;
        public static final int END = 2;
	}
}
