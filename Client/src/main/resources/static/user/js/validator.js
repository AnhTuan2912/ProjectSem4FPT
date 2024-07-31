// hàm đối tượng
function Validator(options){

    //hàm thực hiện validate
    function validate(inputElement, rule){
        var errorMessage = rule.test(inputElement.value);           
        var errorElement = inputElement.parentElement.querySelector(options.errorSelector);

        if(errorMessage){
            errorElement.innerText = errorMessage;
            inputElement.parentElement.classList.add('invalid');
            return false; // Trả về false nếu có lỗi
        }else{
            errorElement.innerText = '';
            inputElement.parentElement.classList.remove('invalid');
            return true; // Trả về true nếu hợp lệ
        }
    }

    //lấy element của form cần validate
    var formElement = document.querySelector(options.form);
   

    if(formElement){
        formElement.onsubmit = function(event){
            event.preventDefault();
            var isFormValid = true;
            options.rules.forEach(function (rule) {
                var inputElement = formElement.querySelector(rule.selector);

                if (inputElement) {
                    var isValid = validate(inputElement, rule); // Kiểm tra hợp lệ của từng input

                    if (!isValid) {
                        isFormValid = false; // Nếu có ít nhất một input không hợp lệ, đánh dấu form không hợp lệ
                    }
                }
            });
            if (isFormValid) {
                formElement.submit(); // Nếu form hợp lệ, thực hiện submit
            }
        };



        options.rules.forEach(function (rule){
            var inputElement = formElement.querySelector(rule.selector);

            if(inputElement){
                //xử lý trường hợp blur khỏi input
                inputElement.onblur = function(){
                    validate(inputElement, rule);
                 
                };
                //xử lí trường hợp mỗi khi người dùng nhập
                inputElement.oninput = function (){
                    var errorElement = inputElement.parentElement.querySelector('.form-message');
                    errorElement.innerText = '';
                    inputElement.parentElement.classList.remove('invalid');
                };
            }
        });
    }
}

//định nghĩa rules
//nguyên tắc rules:
//1.khi có lỗi => trả ra message lỗi
//2.khi hợp lệ => không trả lại gì
Validator.isRequired = function(selector) {
    return{
        selector: selector,
        test: function(value){
            return value.trim() ? undefined : 'Field cannot blank!';
        }
    };
};

Validator.minLength = function(selector, min) {
    return{
        selector: selector,
        test: function(value){
            return value.length >= min ? undefined : `length from 5- ${min} character`;
        }
    };
};

Validator.positiveNumber = function(selector){
    return{
        selector: selector,
        test: function(value){
            return value > 0 ? undefined : 'Please enter a positive number';
            
        }
    };
};

