package org.example;

import jakarta.servlet.annotation.WebServlet;

@WebServlet("/div")
public class DivServlet extends CalcServlet {
    @Override
    protected int operation(int a, int b) {
        return a / b;
    }
}