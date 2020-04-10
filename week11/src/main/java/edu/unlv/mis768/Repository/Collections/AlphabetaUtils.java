package edu.unlv.mis768.Repository.Collections;

import java.util.HashMap;

import static edu.unlv.mis768.Repository.Collections.Alphabetas.*;
public class AlphabetaUtils {
    public static Alphabetas next(Alphabetas abc) {
        return orderMap.get(abc);
    }
    public static Alphabetas fromInt(int abc) {
        return intMap.get(abc);
    }
    public static Alphabetas fromChar(char abc){
        return charMap.get(abc);
    }
    public static HashMap<Alphabetas, Alphabetas> orderMap ;
    static {
        orderMap = new HashMap<>();
        // print ";\n".join(map(lambda(n) : "intMap.put(" + chr(n) + ", " + chr(n+1) +")" , [ ord('A') + i  for i in range(0, 26) ]))
        orderMap.put(A, B);
        orderMap.put(B, C);
        orderMap.put(C, D);
        orderMap.put(D, E);
        orderMap.put(E, F);
        orderMap.put(F, G);
        orderMap.put(G, H);
        orderMap.put(H, I);
        orderMap.put(I, J);
        orderMap.put(J, K);
        orderMap.put(K, L);
        orderMap.put(L, M);
        orderMap.put(M, N);
        orderMap.put(N, O);
        orderMap.put(O, P);
        orderMap.put(P, Q);
        orderMap.put(Q, R);
        orderMap.put(R, S);
        orderMap.put(S, T);
        orderMap.put(T, U);
        orderMap.put(U, V);
        orderMap.put(V, W);
        orderMap.put(W, X);
        orderMap.put(X, Y);
        orderMap.put(Y, Z);
    }
    public static HashMap<Integer, Alphabetas> intMap ;
    static {
        intMap = new HashMap<>();
        // >>> print ";\n".join(map(lambda(a,b) : "intMap.put(" + str(a) +"," + b + ")" , zip(range(0,26), [chr( ord('A') + i ) for i in range(0, 26) ])))
        intMap.put(0,A);
        intMap.put(1,B);
        intMap.put(2,C);
        intMap.put(3,D);
        intMap.put(4,E);
        intMap.put(5,F);
        intMap.put(6,G);
        intMap.put(7,H);
        intMap.put(8,I);
        intMap.put(9,J);
        intMap.put(10,K);
        intMap.put(11,L);
        intMap.put(12,M);
        intMap.put(13,N);
        intMap.put(14,O);
        intMap.put(15,P);
        intMap.put(16,Q);
        intMap.put(17,R);
        intMap.put(18,S);
        intMap.put(19,T);
        intMap.put(20,U);
        intMap.put(21,V);
        intMap.put(22,W);
        intMap.put(23,X);
        intMap.put(24,Y);
        intMap.put(25,Z);

    }
    public static HashMap<Character, Alphabetas> charMap ;
    static {
        charMap = new HashMap<>();
        // print "\n".join(map(lambda(n) : "charMap.put('" + chr(n) + "', " + chr(n) +");" , [ ord('A') + i  for i in range(0, 26) ]))
        // >>> print "\n".join(map(lambda(n) : "charMap.put('" + chr(n) + "', " + chr(n+ ord('A') - ord('a') ) +");" , [ ord('a') + i  for i in range(0, 26) ]))

        charMap.put('A', A);
        charMap.put('B', B);
        charMap.put('C', C);
        charMap.put('D', D);
        charMap.put('E', E);
        charMap.put('F', F);
        charMap.put('G', G);
        charMap.put('H', H);
        charMap.put('I', I);
        charMap.put('J', J);
        charMap.put('K', K);
        charMap.put('L', L);
        charMap.put('M', M);
        charMap.put('N', N);
        charMap.put('O', O);
        charMap.put('P', P);
        charMap.put('Q', Q);
        charMap.put('R', R);
        charMap.put('S', S);
        charMap.put('T', T);
        charMap.put('U', U);
        charMap.put('V', V);
        charMap.put('W', W);
        charMap.put('X', X);
        charMap.put('Y', Y);
        charMap.put('Z', Z);
        charMap.put('a', A);
        charMap.put('b', B);
        charMap.put('c', C);
        charMap.put('d', D);
        charMap.put('e', E);
        charMap.put('f', F);
        charMap.put('g', G);
        charMap.put('h', H);
        charMap.put('i', I);
        charMap.put('j', J);
        charMap.put('k', K);
        charMap.put('l', L);
        charMap.put('m', M);
        charMap.put('n', N);
        charMap.put('o', O);
        charMap.put('p', P);
        charMap.put('q', Q);
        charMap.put('r', R);
        charMap.put('s', S);
        charMap.put('t', T);
        charMap.put('u', U);
        charMap.put('v', V);
        charMap.put('w', W);
        charMap.put('x', X);
        charMap.put('y', Y);
        charMap.put('z', Z);

    }

}
