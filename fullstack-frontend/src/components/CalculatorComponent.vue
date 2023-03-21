<template>
  <div class="calculator">
    <div id="display" class="display">{{ this.current || '0' }}</div>
    <div id="clear" @click="clear" class="calculator-button">C</div>
    <div id="sign" @click="sign" class="calculator-button">+/-</div>
    <div id="percent" @click="percent" class="calculator-button">%</div>
    <div id="divide" @click="divide" class="calculator-button operator">/</div>
    <div id="seven" @click="insert(7)" class="calculator-button">7</div>
    <div id="eight" @click="insert(8)" class="calculator-button">8</div>
    <div id="nine" @click="insert(9)" class="calculator-button">9</div>
    <div id="multiply" @click="multiply" class="calculator-button operator">x</div>
    <div id="four" @click="insert(4)" class="calculator-button">4</div>
    <div id="five" @click="insert(5)" class="calculator-button">5</div>
    <div id="six" @click="insert(6)" class="calculator-button">6</div>
    <div id="subtract" @click="subtract" class="calculator-button operator">-</div>
    <div id="one" @click="insert(1)" class="calculator-button">1</div>
    <div id="two" @click="insert(2)" class="calculator-button">2</div>
    <div id="three" @click="insert(3)" class="calculator-button">3</div>
    <div id="add" @click="add" class="calculator-button operator">+</div>
    <div id="zero" @click="insert(0)" class="calculator-button zero">0</div>
    <div id="dot" @click="dot" class="calculator-button">.</div>
    <div id="equal" @click="equal" class="calculator-button operator">=</div>
    <div id="history-display" class="history-display">
      <!--History goes here -->
      <ul>
        <li v-for="item in history">{{ item }}</li>
      </ul>
    </div>
  </div>
</template>


<script>
import CalculatorService from '../services/CalculatorService';
import { useLoginStore } from '../store/LoginStore';

export default {
  data() {
    return {
      previous: null,
      current: '',
      operator: null,
      operatorString: '',
      operatorClicked: false,
      history: []
    };
  },
  setup() {

  },
  computed: {
    userID() {
      const loginStore = useLoginStore();
      return loginStore.$state.userID;
    }
  },
  methods: {
    async fetchHistory() {
      try {
        const history = await CalculatorService.getCalculations(this.userID);
        this.history = history;
      } catch (error) {
        console.error("Error fetching history:", error);
      }
    },
    clear() {
      this.current = '';
    },
    sign() {
      this.current =
        this.current.charAt(0) === '-'
          ? this.current.slice(1)
          : `-${this.current}`;
    },
    percent() {
      this.current = `${parseFloat(this.current) / 100}`;
    },
    dot() {
      if (this.current.indexOf('.') === -1) {
        this.insert('.');
      }
    },
    insert(num) {
      if (this.operatorClicked) {
        this.current = '';
        this.operatorClicked = false;
      }
      this.current = `${this.current}${num}`;
    },
    setPrevious(elem) {
      this.previous = elem;
      this.operatorClicked = true;
    },
    divide() {
      /* this.operator = (a, b) => a / b;
      this.setPrevious(this.current); */
      /* this.operator = (a, b) => {
        if (b === 0) {
          this.current = 'Math error';
          this.setPrevious(null);
          return this.current;
        }
        return a / b;
      }; */
      this.setPrevious(this.current);
      this.operatorString = '/';
    },
    multiply() {
      //this.operator = (a, b) => a * b;
      this.setPrevious(this.current);
      this.operatorString = '*';
    },
    subtract() {
      //this.operator = (a, b) => a - b;
      this.setPrevious(this.current);
      this.operatorString = '-';
    },
    add() {
      //this.operator = (a, b) => a + b;
      this.setPrevious(this.current);
      this.operatorString = '+';
    },
    async equal() {
      const expression = {
        numOne: this.previous,
        numTwo: this.current,
        operator: this.operatorString
      }

      try {
        console.log(this.userID);
        const response = await CalculatorService.postExpression(this.userID, expression)
        const result = response.data.result
        this.current = result
        this.fetchHistory()
      } catch (error) {
        console.error('Error submitting expression:', error)
      }
    },
  },
  created() {
    this.fetchHistory();
  },
};
</script>

<style scoped>
.calculator {
  margin: 0 auto;
  width: 400px;
  font-size: 40px;
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  grid-auto-rows: minmax(50px, auto);
}

.display {
  grid-column: 1 / -1;
  background-color: #333;
  color: white;
}

.zero {
  grid-column: 1 / 3;
}

.calculator-button {
  cursor: pointer;
  background-color: #eee;
  border: 1px solid #999;
}

.calculator-button:hover {
  background-color: #ddd;
}

.calculator-button:active {
  background-color: #ccc;
  box-shadow: inset 0 0 10px #999;
}

.operator {
  background-color: orange;
  color: white;
}

.history-display {
  grid-column: 1 / -1;
  background-color: #f0f0f0;
  padding: 15px;
  font-size: 30px;
  box-sizing: border-box;
  overflow-y: scroll;
}

@media (max-height: 400px) {
  .history-display {
    overflow-y: hidden;
  }
}

.history-display ul {
  list-style-type: none;
  margin: 0;
  padding: 0;
}

.history-display li {
  padding: 10px;
  border-bottom: 1px solid #ccc;
}
</style>
