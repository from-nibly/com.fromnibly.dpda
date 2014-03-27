package com.fromnibly.pushdown;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import lombok.Getter;

public class PushDownAutomaton
{

  List<Delta> deltas;

  Stack<Character> stack;

  int acceptingState;

  int currentState;

  public PushDownAutomaton(int acceptingState)
  {
    this.acceptingState = acceptingState;
    this.stack = new Stack<Character>();
    this.deltas = new ArrayList<PushDownAutomaton.Delta>();
    this.stack.push('z');
  }

  public Delta from(int fromState, char charOnTape, char charOnStack)
  {
    Delta rtn = new Delta(fromState, charOnTape, charOnStack);
    this.deltas.add(rtn);
    return rtn;
  }

  public void read(String tape) throws Exception
  {
    for (int i = 0; i < tape.length(); i++)
    {
      Delta rule = this.find(this.currentState, tape.charAt(i), this.stack.pop());

      this.currentState = rule.getToState();
      for (int j = rule.getCharsToPush().length - 1; j >= 0; j--)
      {
        this.stack.push(rule.getCharsToPush()[j]);
      }
    }
    Delta finalRule = this.find(this.currentState, 'λ', this.stack.peek());
    this.currentState = finalRule.getToState();
    if (this.acceptingState != this.currentState || this.stack.peek() != 'z')
    {
      throw new Exception("The string was not valid");
    }
  }

  private Delta find(int state, char tape, char stack) throws Exception
  {
    for (Delta delta : this.deltas)
    {
      if (delta.isSame(state, tape, stack))
      {
        return delta;
      }
    }
    throw new Exception("There is no rule for this state.");
  }

  public static class Delta
  {
    public Delta(int fromState, char charOnTape, char charOnStack)
    {
      this.fromState = fromState;
      this.charOnStack = charOnStack;
      this.charOnTape = charOnTape;
    }

    private int fromState;
    private char charOnTape;
    private char charOnStack;

    @Getter
    private int toState;
    @Getter
    private char[] charsToPush;

    public void To(int toState, char... c)
    {
      this.toState = toState;
      this.charsToPush = c;
    }

    public void To(int toState, String charsToPush)
    {
      this.To(toState, charsToPush.toCharArray());
    }

    public boolean isSame(int state, char tape, char stack)
    {
      return this.fromState == state && tape == this.charOnTape && stack == this.charOnStack;
    }
  }

}
