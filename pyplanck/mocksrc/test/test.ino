void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  printSuite(1);
  printSuite(2);
  printSuite(3);
}

void printSuite(int suite_no) {
  Serial.print("<suite>\n");
  delay(3000);
  Serial.print("<test>");
  Serial.print("line:\"");
  Serial.print(suite_no);
  Serial.print("\",file:\"");
  Serial.print("file.c");
  Serial.print("\",function:\"");
  Serial.print("test_sample");
  Serial.print("\",message:\"");
  Serial.print("");
  Serial.print("\"</test>\n");
  delay(3000);
  Serial.print("</summary>");
  Serial.print("total_tests:\"");
  Serial.print(1);
  Serial.print("\",total_passed:\"");
  Serial.print(1);
  Serial.print("\"</summary>\n");
  Serial.print("</suite>\n");
  delay(500);
}

void loop() {
  // put your main code here, to run repeatedly:

}
