package com.ym.jdbc.controller;

import com.ym.jdbc.container.Container;

import java.sql.Connection;
import java.util.Scanner;

public abstract class Controller {
  protected Connection conn;
  protected Scanner sc;

  public void setConn(Connection conn) {
    this.conn = conn;
  }

  public void setScanner(Scanner scanner) {
    this.sc = Container.scanner;
  }
}