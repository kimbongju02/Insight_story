const chat_div = document.querySelector('.chat');
const options = document.querySelector('.options');
const history = document.querySelector('.history');
const story_id = document.querySelector('.container').id;

var part_cnt = 0;
const data_history = {};
const choice_history = {};
var option_cnt=0;

window.onload = function() {
    laod_start_story(story_id);

    var historyContainer = document.querySelector('.history');
    historyContainer.scrollTop = historyContainer.scrollHeight;
};

// 게임을 시작할 때 첫 스토리 설명 생성 함수
function laod_start_story(id){
    // fetch('/api/story')
    fetch('/generate/init/'+id)
    .then(response => response.json())
    .then(data => {
        create_chat_div(data);
    })
    .catch(error => console.error('Error:', error));
}

// 스토리를 생성할 때 스토리마다 div 영역을 생성하여 추가
async function create_chat_div(data) {
    await disable_history();
    const part_container = document.createElement('div');
    part_container.id = 'part-' + part_cnt;
    part_container.setAttribute('data-value', part_cnt);
    chat_div.appendChild(part_container);
    await add_story(part_container, data['story']);
    await add_dialogue(part_container, data['dialogue']);
    await add_option(data['choice1'], data['choice2'], data['choice3']);
    
    select_button_event(part);
    save_data_history(data);
    enable_history();
}

// 스토리를 생성할 때 사용자가 분기 버튼을 클릭하지 못하도록 설정
function disable_history(){
    const history_button = document.querySelectorAll('.history button');
    history_button.forEach(function(button){
        button.disabled = true;
    })
}

// 스토리 생성이 종료된 후 사용자가 분기 버튼을 클릭할 수 있도록 설정
function enable_history(){
    const history_button = document.querySelectorAll('.history button');
    history_button.forEach(function(button){
        button.disabled = false;
    })
}

// create_chat_div 함수에 생성된 스토리 영역을 추가
function add_story(part, story) {
    return new Promise((resolve) => {
        const story_element = document.createElement('div');
        story_element.classList.add('story');
        part.appendChild(story_element);
        one_word_one_time(story_element, story).then(resolve);
    });
}

// create_chat_div 함수에 생성된 대화 영역을 추가
function add_dialogue(select_part_container, dialogue){
    return new Promise(async (resolve) => {
        for (const item of dialogue) {
            const dialogue_element = document.createElement('div');
            dialogue_element.classList.add('chat-bubble');
            const dialogue_text = item.name + ": " + item.content;
            select_part_container.appendChild(dialogue_element);
            await one_word_one_time(dialogue_element, dialogue_text);
        }
        resolve();
    });
}

// create_chat_div 함수에 생성된 선택지 영역을 추가
function add_option(choice1, choice2, choice3){
    return new Promise((resolve) => {
        const option1_element = document.createElement('button');
        const option2_element = document.createElement('button');
        const option3_element = document.createElement('button');

        option1_element.id = 'option';
        option2_element.id = 'option';
        option3_element.id = 'option';
        if(choice1!= null)
            options.appendChild(option1_element);
        if(choice2!= null)
            options.appendChild(option2_element);
        if(choice3!= null)
            options.appendChild(option3_element);

        option1_element.textContent = choice1;
        option2_element.textContent = choice2;
        option3_element.textContent = choice3;

        resolve();
    });
}

// 사용자가 선택지 버튼 클릭 시 수행
// 선택한 선택지를 분기 영역에 추가 및 create_chat_div 영역에도 추가
function select_button_event(select_part_container){
    return new Promise((resolve) => {
        const buttons = document.querySelectorAll('.options button');
        buttons.forEach(button => {
            button.addEventListener('click', function() {
                const click_button = document.getElementById(this.id);
                const my_select_option = document.createElement('div');
                my_select_option.classList.add('my-chat-bubble');
                const select_button_text = click_button.textContent;
                my_select_option.textContent = select_button_text;
                select_part_container.appendChild(my_select_option);

                // 사용자가 선택지 클릭 시 분기 영역에 선택지 추가
                add_history(select_button_text);

                create_next_story();
                part_cnt += 1;

                options.innerHTML = ''; 
            });
        });
    });
};

// 사용자가 선택지 버튼 클릭 시 다음 스토리 생성을 요청
function create_next_story(){
    fetch("/generate/story", {
        method: 'POST',
        body: JSON.stringify({
            //data: data_history[part_cnt-1],
            data: JSON.stringify(data_history[part_cnt]),
            choice: choice_history[part_cnt],
        }),
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => response.json())
    .then(data => {
        create_chat_div(data);
    })
    .catch(error => {
        console.error('error occur:', error);
    });
}

// 생성된 스토리가 한글자 씩 출력되도록 설정
function one_word_one_time(div, story){
    return new Promise((resolve) => {
        let index = 0;
        const interval = 10;

        const intervalId = setInterval(() => {
            if (index < story.length) {
                div.textContent += story[index];
                index++;
            } else {
                clearInterval(intervalId);
                resolve();
            }
        }, interval);
    });
}

// 사용자가 선택지 클릭 시 분기 영역에 선택지 추가
function add_history(select_button_text){
    const history_element = document.createElement('button');
    history_element.id = "history-" + part_cnt;
    history_element.setAttribute('data-value', part_cnt);
    history_element.classList.add('info-box');
    history_element.appendChild(history_element);
    history_element.textContent = select_button_text;

    save_choice_history(select_button_text);

    history_element.addEventListener('click', function() {
        const select_part_num = parseInt(this.getAttribute('data-value'));
        const prev_data = data_history[select_part_num];
        console.log("select_part_num: " + select_part_num+" part_cnt: " + part_cnt);

        document.querySelectorAll('.options button').forEach(button => button.remove());
        for(let i=part_cnt; i>=select_part_num; i--){
            console.log("delete part_cnt: " + i);
            const part_container = document.getElementById('part-' + (i));
            const history_container = document.getElementById('history-' + (i));
            
            if(part_container){
                part_container.remove();
            }
            if(history_container){
                history_container.remove();
            }
            delete_data_history(i);
            delete_choice_history(i);
        }
        part_cnt = select_part_num;
        create_chat_div(prev_data)
    });
}

// 분기를 클릭하여 돌아갈 때 스토리 전달을 위해 사용하는 함수들
function save_data_history(data){
    data_history[part_cnt] = data;
    console.log(data_history);
}

function delete_data_history(part_cnt){
    delete data_history[part_cnt];
    console.log(data_history);
}

function save_choice_history(choice){
    choice_history[part_cnt] = choice;
    console.log(choice_history);
}

function delete_choice_history(part_cnt){
    delete choice_history[part_cnt];
    console.log(choice_history);
}