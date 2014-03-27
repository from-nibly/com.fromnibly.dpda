package com.fromnibly.stateMachine;

import org.junit.Test;

public class PushDownAutomatonTest
{

  @Test
  public void test() throws Exception
  {
    PushDownAutomaton pda = new PushDownAutomaton(1);

    pda.from(0, 'a', 'z').To(0, "1z");
    pda.from(0, 'b', 'z').To(0, "1z");
    pda.from(0, 'c', 'z').To(0, "2z");

    pda.from(0, 'a', '1').To(0, "11");
    pda.from(0, 'b', '1').To(0, "11");
    pda.from(0, 'c', '1').To(0, "");

    pda.from(0, 'a', '2').To(0, "2");
    pda.from(0, 'b', '2').To(0, "2");
    pda.from(0, 'c', '2').To(0, "22");

    pda.from(0, 'λ', 'z').To(1, 'z');

    pda.read("acacbbcc");
  }

}
