package be.vdab.web;


import javax.validation.constraints.NotNull;

import be.vdab.constraints.Postcode;

class VanTotPostcodeForm { 
@NotNull
@Postcode
private Integer vanpostcode;
@NotNull
@Postcode
private Integer totpostcode;



public Integer getVanpostcode() {
	return vanpostcode;
}
public Integer getTotpostcode() {
	return totpostcode;
}




VanTotPostcodeForm() { // default constructor (package visibility)
}
// constructor om command object te initialiseren vanuit Controller:
VanTotPostcodeForm(int vanpostcode, int totpostcode) {
this.vanpostcode = vanpostcode;
this.totpostcode = totpostcode;
}
@Override
public String toString() {
return String.format("%s-%s", vanpostcode, totpostcode);
}

public boolean isValid() {
if (vanpostcode == null || totpostcode == null) {
return false;
}
return vanpostcode <= totpostcode;
}


}