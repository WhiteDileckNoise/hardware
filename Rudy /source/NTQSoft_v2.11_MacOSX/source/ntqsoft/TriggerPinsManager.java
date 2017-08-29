package ntqsoft;

import java.util.ArrayList;
import java.util.List;

import controls.ListSelection;

import processing.core.PApplet;

public class TriggerPinsManager {

	private PApplet parent;
	private int xPos;
	private int yPos;
	private int nPins;
	private int maxPins = 20;
	private Configurator config;
	private List<ListSelection> triggers;

	public TriggerPinsManager(PApplet parent, int x, int y, Configurator config) {
		this.parent = parent;
		this.xPos = x;
		this.yPos = y;
		this.config = config;
		this.nPins = config.getNumTriggers();
		this.triggers = new ArrayList<ListSelection>();

		init();
	}

	private void init() {

		int x = xPos;
		int y = yPos;

		for (int i=0; i<20; i++) {
			triggers.add(new ListSelection(parent, "trigger"+(i+1), x, y, 150, 15, "Trigger "+(i+1), config.getAvailablePins(), config.getTriggerPin(i)));
			if (i==9) {
				x += 180;
				y = yPos;
			}
			else {
				y+=23;
			}
		}
		disable();
	}

	public void disable() {
		for (int i=nPins; i<triggers.size(); i++) {
			triggers.get(i).hide();
		}
	}


	public void setNumPins(int numPins) {
		if (numPins < nPins) {
			nPins = numPins;
			disable();
		}
		else if (numPins > nPins && numPins <= maxPins){
			for(int i=nPins; i<numPins; i++) {
				triggers.get(i).show();
			}
			nPins = numPins;
		}
	}

}
