package kr.ac.kopo.kkssmm.bookmarket.controller;

import jakarta.validation.Valid;
import kr.ac.kopo.kkssmm.bookmarket.domain.Member;
import kr.ac.kopo.kkssmm.bookmarket.domain.MemberFormDTO;
import kr.ac.kopo.kkssmm.bookmarket.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/member")
public class MemberController {
    @Autowired
    MemberService memberService;
    @Autowired
    PasswordEncoder passwordEncoder;

    /*  회원가입 폼 출력 */
    @GetMapping(value = "/add")
    public String RequestAddMemberForm(Model model) {
        model.addAttribute("memberFormDTO", new MemberFormDTO());
        return "/member/addMember";
    }
    /* 새로운 회원 Entity 추가 */
    @PostMapping(value = "/add")
    public String submitAddNewMember(@Valid MemberFormDTO memberFormDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/member/addMember";
        }
        try{
            Member member = Member.createMember(memberFormDTO, passwordEncoder);
            memberService.saveMember(member);
        }catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "/member/addMember";
        }
        return "redirect:/members";
    }
    /* 회원 수정 폼 */
    @GetMapping(value = "/update/{memberId}")
    public String RequestUpdateMemberForm(@PathVariable("memberId") String memberId, Model model) {
        Member member = memberService.getMemberByMemberId(memberId);
        model.addAttribute("memberFormDTO", member);
        return "/member/updateMember";
    }
    /* 회원 수정 기능 */
    @PostMapping(value = "/update")
    public String submitUpdateMember(@Valid MemberFormDTO memberFormDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/member/updateMember";
        }
        try{
            Member member = Member.createMember(memberFormDTO, passwordEncoder);
            memberService.saveMember(member);
        }catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "/member/updateMember";
        }
        return "redirect:/members";
    }
    /* 회원 삭제 */
    @GetMapping(value = "/delete/{memberId}")
    public String DeleteMember(@PathVariable("memberId") String memberId) {
        memberService.deleteMember(memberId);
        return "redirect:/logout";
    }
}