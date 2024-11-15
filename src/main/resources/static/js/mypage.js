    // 정보를 수정하는 div의 display를 변경하는 함수
    function userinfo() {
        var userInfoDiv = document.getElementById("userinfo");

        // 현재 display 속성이 'none'일 경우 'block'으로 바꿔서 보이게
        if (userInfoDiv.style.display === "none") {
            userInfoDiv.style.display = "block";
        }
    }

    function xxx() {
        var userInfoDiv = document.getElementById("userinfo");
        userInfoDiv.style.display = "none";

        document.getElementById('userinfo-form').reset();  // 폼 리셋
    }

    function checkEmail() {
        const email = document.getElementById("newemail").value;
        const exemail = document.getElementById("newemail").dataset.email; // Original email from the data attribute

        fetch(`/signup/email-check?email=${encodeURIComponent(email)}`)
            .then(response => response.json())
            .then(data => {
                if (data.exists) {
                    alert("이미 사용된 이메일 입니다.");
                    document.getElementById("emailChecked").value = "false";
                    // Reset the email field to the original value stored in the data attribute
                    document.getElementById("newemail").value = exemail;
                } else {
                    alert("사용 가능한 이메일 입니다.");
                    document.getElementById("emailChecked").value = "true";
                    // Email is valid, you can keep the current input email
                    document.getElementById("newemail").value = email;
                }
            })
            .catch(error => {
                alert("다시 입력해주세요");
            });
    }

    // 비밀번호 조건을 확인하는 함수
    function checkPassword() {
        const pw = document.getElementById("pw").value;

        // 비밀번호 조건 체크
        const allConditionsMet =
            /[A-Za-z]/.test(pw) &&  // 영문자가 하나 이상
            /\d/.test(pw) &&  // 숫자가 하나 이상
            /[@$!%*?&]/.test(pw) &&  // 특수문자가 하나 이상
            pw.length >= 8;  // 길이가 8자 이상

        if (!allConditionsMet) {
            alert("비밀번호는 최소 8자 이상이어야 하며, 영문자, 숫자, 특수문자를 각각 하나 이상 포함해야 합니다.");
            return false;  // 폼 전송을 막습니다.
        }
        return true;  // 조건을 만족하면 폼이 전송됩니다.
    }