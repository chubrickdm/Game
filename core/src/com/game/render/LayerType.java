package com.game.render;

public enum LayerType{
	unknown,
	below, //всегда рисуется под всеми спрайтами
	normal, //рисуется впорядке позиции
	over //всегда рисуется над всеми спрайтами
}
