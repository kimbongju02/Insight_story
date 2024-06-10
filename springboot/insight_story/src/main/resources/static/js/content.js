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

function laod_start_story(id){
    // fetch('/api/story')
    fetch('/generate/init/'+id)
    .then(response => response.json())
    .then(data => {
        create_chat_div(data);
    })
    .catch(error => console.error('Error:', error));
}

async function create_chat_div(data) {
    await disable_history();
    const part = document.createElement('div');
    part.id = 'part-' + part_cnt;
    part.setAttribute('data-value', part_cnt);
    chat_div.appendChild(part);
    await add_story(part, data['story']);
    await add_dialogue(part, data['dialogue']);
    await add_option(data['choice1'], data['choice2'], data['choice3']);
    
    select_button_event(part);
    save_data_history(data);
    enable_history();
}

function disable_history(){
    const history_button = document.querySelectorAll('.history button');
    history_button.forEach(function(button){
        button.disabled = true;
    })
}

function enable_history(){
    const history_button = document.querySelectorAll('.history button');
    history_button.forEach(function(button){
        button.disabled = false;
    })
}

function add_story(part, story) {
    return new Promise((resolve) => {
        const story_container = document.createElement('div');
        story_container.classList.add('story');
        part.appendChild(story_container);
        one_word_one_time(story_container, story).then(resolve);
    });
}

function add_dialogue(part, dialogue){
    return new Promise(async (resolve) => {
        for (const item of dialogue) {
            const dialogue_container = document.createElement('div');
            dialogue_container.classList.add('chat-bubble');
            const dialogue_text = item.name + ": " + item.content;
            part.appendChild(dialogue_container);
            await one_word_one_time(dialogue_container, dialogue_text);
        }
        resolve();
    });
}

function add_option(choice1, choice2, choice3){
    return new Promise((resolve) => {
        const option1_container = document.createElement('button');
        const option2_container = document.createElement('button');
        const option3_container = document.createElement('button');

        option1_container.id = 'option'+option_cnt;
        option_cnt+=1;
        option2_container.id = 'option'+option_cnt;
        option_cnt+=1;
        option3_container.id = 'option'+option_cnt;
        option_cnt+=1;
        options.appendChild(option1_container);
        options.appendChild(option2_container);
        options.appendChild(option3_container);

        option1_container.textContent = choice1;
        option2_container.textContent = choice2;
        option3_container.textContent = choice3;

        resolve();
    });
}

function select_button_event(part){
    return new Promise((resolve) => {
        const buttons = document.querySelectorAll('.options button');
        buttons.forEach(button => {
            button.addEventListener('click', function() {
                const click_button = document.getElementById(this.id);
                const my_select_option = document.createElement('div');
                my_select_option.classList.add('my-chat-bubble');
                const select_button_text = click_button.textContent;
                my_select_option.textContent = select_button_text;
                part.appendChild(my_select_option);
                add_history(select_button_text);

                create_next_story();
                part_cnt += 1;

                options.innerHTML = ''; 
            });
        });
    });
};

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

function add_history(select_button_text){
    const history_container = document.createElement('button');
    history_container.id = "history-" + part_cnt;
    history_container.setAttribute('data-value', part_cnt);
    history_container.classList.add('info-box');
    history.appendChild(history_container);
    history_container.textContent = select_button_text;

    save_choice_history(select_button_text);

    history_container.addEventListener('click', function() {
        const select_part_num = parseInt(this.getAttribute('data-value'));
        const prev_data = data_history[select_part_num];
        console.log("select_part_num: " + select_part_num+" part_cnt: " + part_cnt);

        document.querySelectorAll('.options button').forEach(button => button.remove());
        for(let i=part_cnt; i>=select_part_num; i--){
            console.log("delete part_cnt: " + i);
            const partElement = document.getElementById('part-' + (i));
            const historyElement = document.getElementById('history-' + (i));
            
            if(partElement){
                partElement.remove();
            }
            if(historyElement){
                historyElement.remove();
            }
            delete_data_history(i);
            delete_choice_history(i);
        }
        part_cnt = select_part_num;
        create_chat_div(prev_data)
    });
}

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