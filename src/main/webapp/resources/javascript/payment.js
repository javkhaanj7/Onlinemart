/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



// Create an object
var creditCardValidator = {
    // Pin the cards to them
    'cards': {
        'mc': '5[1-5][0-9]{14}',
        'vi': '4(?:[0-9]{12}|[0-9]{15})',
        'ax': '3[47][0-9]{13}'

    },
    // Add the structure validator to them
    'validateStructure': function (value, ccType) {
        value = String(value).replace(/[^0-9]/g, ''); // ignore dashes and whitespaces - We could even ignore all non-numeric chars (/[^0-9]/g)

        var cardinfo = creditCardValidator.cards,
                results = [];

        if (ccType) {
            // alert(cardinfo[ccType]);
            var expr = '^' + cardinfo[ccType.toLowerCase()] + '$';
            // alert(expr);
            return expr ? !!value.match(expr) : false; // boolean
        }

        for (var i in cardinfo) {
            if (value.match('^' + cardinfo[i] + '$')) {
                results.push(i);
            }
        }
        return results.length ? results.join('|') : false; // String | boolean
    },
    // Add the Luhn validator to them
    'validateChecksum': function (value) {
        // alert(value);
        value = String(value).replace(/[^0-9]/g, ''); // ignore dashes and whitespaces - We could even ignore all non-numeric chars (/[^0-9]/g)

        var sum = 0,
                parity = value.length % 2;

        for (var i = 0; i <= (value.length - 1); i++) { // We'll iterate LTR - it's faster and needs less calculating
            var digit = parseInt(value[i], 10);

            if (i % 2 == parity) {
                digit = digit * 2;
            }
            if (digit > 9) {
                digit = digit - 9; // get the cossfoot - Exp: 10 - 9 = 1 + 0 | 12 - 9 = 1 + 2 | ... | 18 - 9 = 1 + 8
            }

            sum += digit;
        }
        // alert(((sum % 10) == 0));
        return ((sum % 10) == 0); // divide by 10 and check if it ends in 0 - return true | false
    },
    // Apply both validations
    'validate': function (value, ccType) {
        ;
        if (this.validateChecksum(value)) {
            return this.validateStructure(value, ccType);
        }
        return false;
    }
};

// Example
$(document).ready(function () {
    $("#payment_form").submit(function (e) {

        var card = $(".selectcard").val(), number = $(".cardnumber").val();
        //  alert(card + "===" + number);
        if (card == 'VISA')
            card = 'vi';
        if (card == 'MASTER')
            card = 'mc'
        if (card == 'AMEX')
            card = 'ax';
        var valid = creditCardValidator.validate(number, card);
        // alert(valid);
        if (!valid) {
            // alert('asdfadfs')
          //  alert($('.cardnumber').parent());
            $(".dd_number span.error").remove();
            $(".cardnumber").parent().append('<span class="error">Your card is invalid!</span>');
            e.preventDefault();
        }



    });
});

