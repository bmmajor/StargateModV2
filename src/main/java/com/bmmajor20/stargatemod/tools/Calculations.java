package com.bmmajor20.stargatemod.tools;

public class Calculations {

    public static int getGUIStringLength(String string) {
        int length = 0;
        for (char c : string.toCharArray()) {
            //CHECKED CASES: A-Z, a-z, 0-9, ' ', +-*/=_, (){}[],
            //Most of these are unlisted, and are part of the default case.
            //Other unchecked cases will also go to the default
            switch (c) {
                //TODO All these cases will become default
                case 'A':
                case 'B':
                case 'C':
                case 'D':
                case 'E':
                case 'F':
                case 'G':
                case 'H':
                case 'J':
                case 'K':
                case 'L':
                case 'M':
                case 'N':
                case 'O':
                case 'P':
                case 'Q':
                case 'R':
                case 'S':
                case 'T':
                case 'U':
                case 'V':
                case 'W':
                case 'X':
                case 'Y':
                case 'Z':
                case 'a':
                case 'b':
                case 'c':
                case 'd':
                case 'e':
                case 'g':
                case 'h':
                case 'j':
                case 'm':
                case 'n':
                case 'o':
                case 'p':
                case 'q':
                case 'r':
                case 's':
                case 'u':
                case 'v':
                case 'w':
                case 'x':
                case 'y':
                case 'z':
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                case '+':
                case '-':
                case '/':
                case '=':
                case '_':
                    length += 6;
                    break;
                case 'f':
                case 'k':
                    length += 5;
                    break;
                case 'I':
                case 't':
                case ' ':
                case '*':
                case '(':
                case ')':
                case '{':
                case '}':
                case '[':
                case ']':
                    length += 4;
                    break;
                case 'l':
                    length += 3;
                    break;
                case 'i':
                    length += 2;
                    break;
                default:
                    length += 6;
                    break;
            }
        }
        return length;
    }
}
