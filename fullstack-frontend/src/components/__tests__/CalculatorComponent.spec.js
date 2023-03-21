import CalculatorComponent from '@/components/CalculatorComponent.vue';
import { mount } from '@vue/test-utils';
import { describe, expect, it } from 'vitest';

describe('CalculatorComponent', () => {
  it('renders the component correctly', () => {
    const wrapper = mount(CalculatorComponent);

    // Check that the display shows 0 by default
    expect(wrapper.find('#display').text()).toBe('0');
    
    // Check that all the buttons are present
    expect(wrapper.find('#clear').text()).toBe('C');
    expect(wrapper.find('#sign').text()).toBe('+/-');
    expect(wrapper.find('#percent').text()).toBe('%');
    expect(wrapper.find('#divide').text()).toBe('/');
    expect(wrapper.find('#seven').text()).toBe('7');
    expect(wrapper.find('#eight').text()).toBe('8');
    expect(wrapper.find('#nine').text()).toBe('9');
    expect(wrapper.find('#multiply').text()).toBe('x');
    expect(wrapper.find('#four').text()).toBe('4');
    expect(wrapper.find('#five').text()).toBe('5');
    expect(wrapper.find('#six').text()).toBe('6');
    expect(wrapper.find('#subtract').text()).toBe('-');
    expect(wrapper.find('#one').text()).toBe('1');
    expect(wrapper.find('#two').text()).toBe('2');
    expect(wrapper.find('#three').text()).toBe('3');
    expect(wrapper.find('#add').text()).toBe('+');
    expect(wrapper.find('#zero').text()).toBe('0');
    expect(wrapper.find('#dot').text()).toBe('.');
    expect(wrapper.find('#equal').text()).toBe('=');

    // Check that history display is initially empty
    expect(wrapper.find('#history-display').text()).toBe('');

    // Check that the data variables are initially set correctly
    expect(wrapper.vm.previous).toBeNull();
    expect(wrapper.vm.current).toBe('');
    expect(wrapper.vm.operator).toBeNull();
    expect(wrapper.vm.operatorString).toBe('');
    expect(wrapper.vm.operatorClicked).toBe(false);
    expect(wrapper.vm.history).toEqual([]);
  });

  it('adds numbers correctly', () => {
    const wrapper = mount(CalculatorComponent);

    // Click some numbers and the add button
    wrapper.find('#one').trigger('click');
    wrapper.find('#two').trigger('click');
    wrapper.find('#add').trigger('click');
    wrapper.find('#three').trigger('click');
    wrapper.find('#four').trigger('click');
    wrapper.find('#equal').trigger('click');

    // Check that the display shows the correct result
    expect(wrapper.find('#display').text()).toBe('46');

    // Check that the history shows the correct calculation
    expect(wrapper.find('#history-display li').text()).toBe('12 + 34 = 46');
  });

  it('handles division by zero', () => {
    const wrapper = mount(CalculatorComponent);

    // Click some numbers and the divide button, including 0
    wrapper.find('#four').trigger('click');
    wrapper.find('#divide').trigger('click');
    wrapper.find('#zero').trigger('click');
    wrapper.find('#equal').trigger('click');


    // Check that the history shows the correct error message
    expect(wrapper.find('#history-display li').text()).toBe('4 / 0 = Math error');
  });
});
