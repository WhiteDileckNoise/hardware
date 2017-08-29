package ntqsoft;

import java.util.HashMap;

public class NTQFont {

	private HashMap<Character, NTQChar> characters;
	
	public NTQFont() {
		characters = new HashMap<Character, NTQChar>();
		init();
	}
	
	public void init() {
		characters.put('A', new NTQChar(5,7,"01110100011000111111100011000110001"));
		characters.put('B', new NTQChar(5,7,"11110100011000111110100011000111110"));
		characters.put('C', new NTQChar(5,7,"01110100011000010000100001000101110"));
		characters.put('D', new NTQChar(5,7,"11110100011000110001100011000111110"));
		characters.put('E', new NTQChar(5,7,"11111100001000011100100001000011111"));
		characters.put('F', new NTQChar(5,7,"11111100001000011100100001000010000"));
		characters.put('G', new NTQChar(5,7,"01110100011000010000100111000101110"));
		characters.put('H', new NTQChar(5,7,"10001100011000111111100011000110001"));
		characters.put('I', new NTQChar(3,7,"111010010010010010111"));
		characters.put('J', new NTQChar(5,7,"00001000010000100001100011000101110"));
		characters.put('K', new NTQChar(5,7,"10001100101010011000101001001010001"));
		characters.put('L', new NTQChar(5,7,"10000100001000010000100001000011111"));
		characters.put('M', new NTQChar(5,7,"10001110111010110101100011000110001"));
		characters.put('N', new NTQChar(5,7,"10001100011100110101100111000110001"));
		characters.put('O', new NTQChar(5,7,"01110100011000110001100011000101110"));
		characters.put('P', new NTQChar(5,7,"11110100011000111110100001000010000"));
		characters.put('Q', new NTQChar(5,7,"01110100011000110001101011001101110"));
		characters.put('R', new NTQChar(5,7,"11110100011000111110101001001010001"));
		characters.put('S', new NTQChar(5,7,"01110100011000001110000011000101110"));
		characters.put('T', new NTQChar(5,7,"11111001000010000100001000010000100"));
		characters.put('U', new NTQChar(5,7,"10001100011000110001100011000101110"));
		characters.put('V', new NTQChar(5,7,"10001100011000110001010100101000100"));
		characters.put('W', new NTQChar(5,7,"10001100011000110101101010101001010"));
		characters.put('X', new NTQChar(5,7,"10001100010101000100010101000110001"));
		characters.put('Y', new NTQChar(5,7,"10001100010101000100001000010000100"));
		characters.put('Z', new NTQChar(5,7,"11111000010001000100010001000011111"));
		characters.put('a', new NTQChar(4,7,"0000000001110001011110011111"));
		characters.put('b', new NTQChar(4,7,"1000100011101001100110011110"));
		characters.put('c', new NTQChar(4,7,"0000000001111000100010000111"));
		characters.put('d', new NTQChar(4,7,"0001000101111001100110010111"));
		characters.put('e', new NTQChar(4,7,"0000000001101001111110000111"));
		characters.put('f', new NTQChar(3,7,"000011100100110100100"));
		characters.put('g', new NTQChar(4,7,"0000000001111001011100011110"));
		characters.put('h', new NTQChar(4,7,"0000100010001110100110011001"));
		characters.put('i', new NTQChar(1,7,"0101111"));
		characters.put('j', new NTQChar(3,7,"000001000001001001110"));
		characters.put('k', new NTQChar(4,7,"0000100010001010110010101001"));
		characters.put('l', new NTQChar(1,7,"1111111"));
		characters.put('m', new NTQChar(5,7,"00000000000101010101101011010110101"));
		characters.put('n', new NTQChar(4,7,"0000000001101001100110011001"));
		characters.put('o', new NTQChar(4,7,"0000000001101001100110010110"));
		characters.put('p', new NTQChar(4,7,"0000000011101001111010001000"));
		characters.put('q', new NTQChar(4,7,"0000000001111001011100010001"));
		characters.put('r', new NTQChar(4,7,"0000000010111100100010001000"));
		characters.put('s', new NTQChar(4,7,"0000000001111000011000011110"));
		characters.put('t', new NTQChar(4,7,"0000010011100100010001000011"));
		characters.put('u', new NTQChar(4,7,"0000000010011001100110010110"));
		characters.put('v', new NTQChar(5,7,"00000000001000110001010100101000100"));
		characters.put('w', new NTQChar(5,7,"00000000001010110101101011010101010"));
		characters.put('x', new NTQChar(4,7,"0000000010011001011010011001"));
		characters.put('y', new NTQChar(4,7,"0000000010011001011100011110"));
		characters.put('z', new NTQChar(5,7,"00000000001111100010001000100011111"));
		characters.put('1', new NTQChar(4,7,"0010011010100010001000100111"));
		characters.put('2', new NTQChar(4,7,"0110100110010010010010001111"));
		characters.put('3', new NTQChar(4,7,"0110100100010010000110010110"));
		characters.put('4', new NTQChar(4,7,"1000100010001010111100100010"));
		characters.put('5', new NTQChar(4,7,"1111100011100001000110010110"));
		characters.put('6', new NTQChar(4,7,"0110100110001110100110010110"));
		characters.put('7', new NTQChar(4,7,"1111000100100100010001000100"));
		characters.put('8', new NTQChar(4,7,"0110100110010110100110010110"));
		characters.put('9', new NTQChar(4,7,"0110100110010111000110010110"));
		characters.put('0', new NTQChar(4,7,"0110100110011001100110010110"));
		characters.put('-', new NTQChar(3,7,"000000000111000000000"));
		characters.put('_', new NTQChar(4,7,"0000000000000000000000001111"));
		characters.put('(', new NTQChar(3,7,"001010100100100010001"));
		characters.put(')', new NTQChar(3,7,"100010001001001010100"));
		characters.put('[', new NTQChar(2,7,"11101010101011"));
		characters.put(']', new NTQChar(2,7,"11010101010111"));
		characters.put('.', new NTQChar(1,7,"0000001"));
		characters.put(':', new NTQChar(1,7,"0010100"));
		characters.put('=', new NTQChar(4,7,"0000000011110000111100000000"));
		characters.put('+', new NTQChar(5,7,"00000001000010011111001000010000000"));
		characters.put('&', new NTQChar(5,7,"01000101001010001000101011001001101"));
		characters.put('?', new NTQChar(5,7,"01110100010000100110001000000000100"));
		characters.put('!', new NTQChar(1,7,"1111101"));
		characters.put('%', new NTQChar(7,7,"0100001101001001001000001000001001001001011000010"));
		characters.put('/', new NTQChar(7,7,"0000001000001000001000001000001000001000001000000"));
		characters.put('\\', new NTQChar(7,7,"1000000010000000100000001000000010000000100000001"));
		characters.put('@', new NTQChar(7,7,"0011110010000110011011010101100111101000000011111"));
		characters.put('#', new NTQChar(5,7,"00000000000101011111010101111101010"));
		characters.put('"', new NTQChar(3,7,"101101000000000000000"));
		characters.put('\'', new NTQChar(1,7,"1100000"));
		characters.put('¡', new NTQChar(3,7,"010101010000000000000"));
		characters.put('Û', new NTQChar(5,7,"00111010001111001000111100100000111"));
		characters.put('$', new NTQChar(5,7,"00100011111010001110001011111000100"));
		characters.put('£', new NTQChar(5,7,"00110010010010001111001000100011111"));
		}
	
	public void setChar(Character c, NTQChar character) {
		characters.put(c, character);
	}
	
	public NTQChar getChar(Character c) {
		try {
			NTQChar character = characters.get(c);
			return character;
		}
		catch (Exception e) {
			return null;
		}
		
	}
}
