echo "SPECIAL : 5 * X^0 = 5 * X^0"
java -cp build com.computor.Computor "5 * X^0 = 5 * X^0"
echo "\n"

echo "LINEAR : 5.5 * X^0 = 4 * X^0 + 7.2 * X^1"
java -cp build com.computor.Computor "5.5 * X^0 = 4 * X^0 + 7.2 * X^1"
echo "\n"

echo "DIS POS : 5 * X^0 + 13.1 * X^1 + 3 * X^2 = 1 * X^0 + 1 * X^1"
java -cp build com.computor.Computor "5 * X^0 + 13.1 * X^1 + 3 * X^2 = 1 * X^0 + 1 * X^1"
echo "\n"

echo "DIS ZERO : - 4 * X^2 - 12 * X^1 - 5 * X^0 = 4 * X^0"
java -cp build com.computor.Computor "- 4 * X^2 - 12 * X^1 - 5 * X^0 = 4 * X^0"
echo "\n"

echo "DIS NEG: 5 * X^0 + 3 * X^1 + 3 * X^2 = 1 * X^0 + 0 * X^1"
java -cp build com.computor.Computor "5 * X^0 + 3 * X^1 + 3 * X^2 = 1 * X^0 + 0 * X^1"
echo "\n"

echo "FRACTIONS : 1 / X^2 + 2 / X^3 - 3 * X^1 = X^0"
java -cp build com.computor.Computor "1 / X^2 + 2 / X^3 - 3 * X^1 = X^0"
echo "\n"

echo "NEGATIVE VAR : - 5 * X^0 + 13.1 * X^1 + 3 * - X^2 = 1 * X^0 + X"
java -cp build com.computor.Computor "5 * - X^0 + 13.1 * X^1 + 3 * - X^2 = 1 * X^0 + X"
echo "\n"

echo "LONELY VAR : - 5 * X^0 + 13.1 * X^1 + 3 * - X^2 = X^0 + X"
java -cp build com.computor.Computor "5 * - X^0 + 13.1 * X^1 + 3 * - X^2 = 1 * X^0 + X"
echo "\n"

echo "MISSING EXPONENENT : 5 * - X^0 + 13.1 * X + 3 * - X^2 = 1 * X^0 + X"
java -cp build com.computor.Computor "5 * - X^0 + 13.1 * X^1 + 3 * - X^2 = 1 * X^0 + X"
echo "\n"

echo "MULTIPLE VAR : - 4 * X^2 - 1 * Y^1 - 12 * X^1 - 5 * X^0 = 4 * X^0 - 1 * Y^1"
java -cp build com.computor.Computor "- 4 * X^2 - 1 * Y^1 - 12 * X^1 - 5 * X^0 = 4 * X^0 - 1 * Y^1"
echo "\n"

echo "ZERO COEFFICIENT: 0 * X^0 + 0 * X^1 + 3 * X^2 = 1 * X^0 + 0 * X^1"
java -cp build com.computor.Computor "0 * X^0 + 0 * X^1 + 3 * X^2 = 1 * X^0 + 0 * X^1"
echo "\n"


