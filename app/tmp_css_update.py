import re

def process_file(filepath, replacements):
    """Apply replacements to style blocks only in a file."""
    with open(filepath, 'r', encoding='utf-8') as f:
        content = f.read()

    def replace_in_style(match):
        style_text = match.group(0)
        for old, new, desc in replacements:
            if old in style_text:
                style_text = style_text.replace(old, new)
                print(f"  [OK] {desc}")
            else:
                # Try without tab prefixes
                print(f"  [MISS] {desc}")
        return style_text

    new_content = re.sub(r'<style.*?</style>', replace_in_style, content, flags=re.DOTALL)

    if new_content != content:
        with open(filepath, 'w', encoding='utf-8') as f:
            f.write(new_content)
        print(f"  -> File saved with changes")
    else:
        print(f"  -> No changes made")

# ==================== FILE 1: index.vue ====================
print("\n=== File 1: index.vue ===")
process_file(r"D:\xyapp\hyt-app\pages\promotion\index.vue", [
    ("\t\t\tbackground: #FBF4EF;\n\t\t\tborder-radius: 40rpx 40rpx 0rpx 0rpx;",
     "\t\t\tbackground: #FFF8F0;\n\t\t\tborder-radius: 40rpx 40rpx 0rpx 0rpx;",
     ".bottom background FBF4EF -> FFF8F0"),
    ("\t\t\t\tcolor: #000000;\n\t\t\t\toverflow: hidden;",
     "\t\t\t\tcolor: #5D3A1A;\n\t\t\t\tfont-weight: 700;\n\t\t\t\toverflow: hidden;",
     ".bottom-body color 000000 -> 5D3A1A + fw700"),
    ("\t\t\t\tbackground: #FFFFFF;\n\t\t\t\tborder-radius: 20rpx 20rpx 20rpx 20rpx;",
     "\t\t\t\tbackground: #FFFFFF;\n\t\t\t\tborder-radius: 32rpx;\n\t\t\t\tbox-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);",
     ".bottom-body card: border-radius 20->32 + shadow"),
    ("\t\t\t\t\t\t\tcolor: #666666;",
     "\t\t\t\t\t\t\tcolor: #8B6B4A;",
     ".bottom-item color 666666 -> 8B6B4A"),
])

# ==================== FILE 2: index2.vue ====================
print("\n=== File 2: index2.vue ===")
process_file(r"D:\xyapp\hyt-app\pages\promotion\index2.vue", [
    ("\t\tbackground: linear-gradient(180deg, #FDF6EE 0%, #FDE7C1 100%);",
     "\t\tbackground: linear-gradient(180deg, #FFF5EC 0%, #FFF0E0 40%, #FFEBD6 100%);",
     "page gradient"),
    ("\t\t\tcolor: #000000;\n\t\t\tmargin: 30rpx;",
     "\t\t\tcolor: #5D3A1A;\n\t\t\tfont-weight: 700;\n\t\t\tmargin: 30rpx;",
     ".top color 000000 -> 5D3A1A + fw700"),
    ("\t\t\t\t\tcolor: #000000;\n\t\t\t\t}",
     "\t\t\t\t\tcolor: #8B6B4A;\n\t\t\t\t}",
     ".top-info-item2 color 000000 -> 8B6B4A"),
    ("\t\t\t\tcolor: #000000;\n\t\t\t\tmargin-left: 20rpx;",
     "\t\t\t\tcolor: #5D3A1A;\n\t\t\t\tfont-weight: 700;\n\t\t\t\tmargin-left: 20rpx;",
     ".middle-item1 color 000000 -> 5D3A1A + fw700"),
    ("\t\t\tbackground: #FBF4EF;\n\t\t\tborder-radius: 40rpx 40rpx 0rpx 0rpx;",
     "\t\t\tbackground: #FFF8F0;\n\t\t\tborder-radius: 40rpx 40rpx 0rpx 0rpx;",
     ".bottom background FBF4EF -> FFF8F0"),
    ("\t\t\t\tcolor: #000000;\n\t\t\t\toverflow: hidden;",
     "\t\t\t\tcolor: #5D3A1A;\n\t\t\t\tfont-weight: 700;\n\t\t\t\toverflow: hidden;",
     ".bottom-body color 000000 -> 5D3A1A + fw700"),
    ("\t\t\t\tbackground: #FFFFFF;\n\t\t\t\tborder-radius: 20rpx 20rpx 20rpx 20rpx;",
     "\t\t\t\tbackground: #FFFFFF;\n\t\t\t\tborder-radius: 32rpx;\n\t\t\t\tbox-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);",
     ".bottom-body card: border-radius 20->32 + shadow"),
    ("\t\t\t\t\t\t\tcolor: #666666;",
     "\t\t\t\t\t\t\tcolor: #8B6B4A;",
     ".bottom-item color 666666 -> 8B6B4A"),
])

# ==================== FILE 3: index3.vue ====================
print("\n=== File 3: index3.vue ===")
process_file(r"D:\xyapp\hyt-app\pages\promotion\index3.vue", [
    ("\t\tbackground: linear-gradient(180deg, #FDF6EE 0%, #FDE7C1 100%);",
     "\t\tbackground: linear-gradient(180deg, #FFF5EC 0%, #FFF0E0 40%, #FFEBD6 100%);",
     "page gradient"),
    ("\t\t\tcolor: #000000;\n\t\t\tmargin: 30rpx;",
     "\t\t\tcolor: #5D3A1A;\n\t\t\tfont-weight: 700;\n\t\t\tmargin: 30rpx;",
     ".top color 000000 -> 5D3A1A + fw700"),
    ("\t\t\t\t\tcolor: #000000;\n\t\t\t\t}",
     "\t\t\t\t\tcolor: #8B6B4A;\n\t\t\t\t}",
     ".top-info-item2 color 000000 -> 8B6B4A"),
    ("\t\t\t\tcolor: #000000;\n\t\t\t\tmargin-left: 20rpx;",
     "\t\t\t\tcolor: #5D3A1A;\n\t\t\t\tfont-weight: 700;\n\t\t\t\tmargin-left: 20rpx;",
     ".middle-item1 color 000000 -> 5D3A1A + fw700"),
    ("\t\t\tbackground: #FBF4EF;\n\t\t\tborder-radius: 40rpx 40rpx 0rpx 0rpx;",
     "\t\t\tbackground: #FFF8F0;\n\t\t\tborder-radius: 40rpx 40rpx 0rpx 0rpx;",
     ".bottom background FBF4EF -> FFF8F0"),
    ("\t\t\t\tcolor: #000000;\n\t\t\t\toverflow: hidden;",
     "\t\t\t\tcolor: #5D3A1A;\n\t\t\t\tfont-weight: 700;\n\t\t\t\toverflow: hidden;",
     ".bottom-body color 000000 -> 5D3A1A + fw700"),
    ("\t\t\t\tbackground: #FFFFFF;\n\t\t\t\tborder-radius: 20rpx 20rpx 20rpx 20rpx;",
     "\t\t\t\tbackground: #FFFFFF;\n\t\t\t\tborder-radius: 32rpx;\n\t\t\t\tbox-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);",
     ".bottom-body card: border-radius 20->32 + shadow"),
    ("\t\t\t\t\t\t\tcolor: #666666;",
     "\t\t\t\t\t\t\tcolor: #8B6B4A;",
     ".bottom-item color 666666 -> 8B6B4A"),
])

# ==================== FILE 4: applyForAgent.vue ====================
print("\n=== File 4: applyForAgent.vue ===")
process_file(r"D:\xyapp\hyt-app\pages\promotion\applyForAgent.vue", [
    ("\t\t\tbackground: #F5F5F5;\n\t\t\tborder-radius: 40rpx 40rpx 40rpx 40rpx;",
     "\t\t\tbackground: #FFF8F0;\n\t\t\tborder-radius: 32rpx;\n\t\t\tbox-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);",
     ".middle F5F5F5 -> FFF8F0 + border 32 + shadow"),
    ("\t\t\tbackground: #FD4234;\n\t\t\tborder-radius: 200rpx 200rpx 200rpx 200rpx;",
     "\t\t\tbackground: linear-gradient(135deg, #E8783B, #F4A460);\n\t\t\tborder-radius: 200rpx;",
     ".btn FD4234 -> gradient + border 200rpx"),
])

# ==================== FILE 5: bindingCode.vue ====================
print("\n=== File 5: bindingCode.vue ===")
process_file(r"D:\xyapp\hyt-app\pages\promotion\bindingCode.vue", [
    ("\t\t\t\tbackground: #F5F5F5;\n\t\t\t\tborder-radius: 20rpx 20rpx 20rpx 20rpx;",
     "\t\t\t\tbackground: #FFF8F0;\n\t\t\t\tborder-radius: 32rpx;\n\t\t\t\tbox-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);",
     ".fillInCode-bg F5F5F5 -> FFF8F0 + border 32 + shadow"),
    ("\t\t\t\tbackground: #4BCA32;\n\t\t\t\tborder-radius: 16rpx 16rpx 16rpx 16rpx;",
     "\t\t\t\tbackground: linear-gradient(135deg, #E8783B, #F4A460);\n\t\t\t\tborder-radius: 32rpx;",
     ".fillInCode-btn 4BCA32 -> gradient + border 32"),
    ("\t\t\t\tcolor: #2684FF;\n\t\t\t\ttext-align: center;\n\t\t\t\tline-height: 100rpx;\n\t\t\t\tborder-radius: 16rpx 16rpx 16rpx 16rpx;\n\t\t\t\tborder: 2rpx solid #2684FF;",
     "\t\t\t\tcolor: #E8783B;\n\t\t\t\ttext-align: center;\n\t\t\t\tline-height: 100rpx;\n\t\t\t\tborder-radius: 32rpx;\n\t\t\t\tborder: 2rpx solid #E8783B;",
     ".bindingStatus-btn color+border 2684FF->E8783B + border 32"),
    ("\t\t\t\t\tcolor: #999999;",
     "\t\t\t\t\tcolor: #8B6B4A;",
     "input color 999999 -> 8B6B4A"),
])

# ==================== FILE 6: dataStatistics.vue ====================
print("\n=== File 6: dataStatistics.vue ===")
process_file(r"D:\xyapp\hyt-app\pages\promotion\dataStatistics.vue", [
    ("\t\t\tbackground: linear-gradient(180deg, #FDF6EE 0%, #FDE7C1 100%);",
     "\t\t\tbackground: linear-gradient(180deg, #FFF5EC 0%, #FFF0E0 40%, #FFEBD6 100%);",
     ".content gradient"),
    ("\t\t\t\tbackground: #FFFFFF;\n\t\t\t\tborder-radius: 40rpx 40rpx 0rpx 0rpx;",
     "\t\t\t\tbackground: #FFF8F0;\n\t\t\t\tborder-radius: 40rpx 40rpx 0rpx 0rpx;",
     ".bottom background FFFFFF -> FFF8F0"),
])

# ==================== FILE 7: fillInAgent.vue ====================
print("\n=== File 7: fillInAgent.vue ===")
process_file(r"D:\xyapp\hyt-app\pages\promotion\fillInAgent.vue", [
    ("\t\tbackground: #F6F6F6;",
     "\t\tbackground: #FFF8F0;",
     "page background F6F6F6 -> FFF8F0"),
    ("\t\t\tbackground: #FFFFFF;\n\t\t\tborder-radius: 20rpx 20rpx 20rpx 20rpx;\n\n\t\t\t.top-item",
     "\t\t\tbackground: #FFFFFF;\n\t\t\tborder-radius: 32rpx;\n\t\t\tbox-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);\n\n\t\t\t.top-item",
     ".top card: border 20->32 + shadow"),
    ("\t\t\tbackground: #FFFFFF;\n\t\t\tborder-radius: 20rpx 20rpx 20rpx 20rpx;\n\n\t\t\t.middle-title",
     "\t\t\tbackground: #FFFFFF;\n\t\t\tborder-radius: 32rpx;\n\t\t\tbox-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);\n\n\t\t\t.middle-title",
     ".middle card: border 20->32 + shadow"),
    ("\t\t\tbackground: #FFFFFF;\n\t\t\tdisplay: flex;\n\t\t\tjustify-content: space-evenly;\n\t\t\tflex-direction: column;\n\t\t\tborder-radius: 20rpx 20rpx 20rpx 20rpx;",
     "\t\t\tbackground: #FFFFFF;\n\t\t\tdisplay: flex;\n\t\t\tjustify-content: space-evenly;\n\t\t\tflex-direction: column;\n\t\t\tborder-radius: 32rpx;\n\t\t\tbox-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);",
     ".bottom card: border 20->32 + shadow"),
    ("\t\t\tbackground: #FFFFFF;\n\t\t\tborder-radius: 20rpx 20rpx 20rpx 20rpx;\n\n\t\t\timage",
     "\t\t\tbackground: #FFFFFF;\n\t\t\tborder-radius: 32rpx;\n\t\t\tbox-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);\n\n\t\t\timage",
     ".transferRecords card: border 20->32 + shadow"),
    ("\t\t\tcolor: #000000;\n\t\t}\n\n\t\t.transferRecords",
     "\t\t\tcolor: #5D3A1A;\n\t\t}\n\n\t\t.transferRecords",
     ".tips color 000000 -> 5D3A1A"),
    ("\t\t\tbackground: #FD4234;\n\t\t\tborder-radius: 200rpx 200rpx 200rpx 200rpx;",
     "\t\t\tbackground: linear-gradient(135deg, #E8783B, #F4A460);\n\t\t\tborder-radius: 200rpx;",
     ".bottom-btn FD4234 -> gradient + border 200rpx"),
])

# ==================== FILE 8: inviteCommander.vue ====================
print("\n=== File 8: inviteCommander.vue ===")
process_file(r"D:\xyapp\hyt-app\pages\promotion\inviteCommander.vue", [
    ("\t\t\t\tbackground: linear-gradient(140deg, #FF7B5C 0%, #FF1226 100%);\n\t\t\t\tborder-radius: 30rpx 30rpx 30rpx 30rpx;",
     "\t\t\t\tbackground: linear-gradient(135deg, #E8783B, #F4A460);\n\t\t\t\tborder-radius: 32rpx;",
     ".body-btn gradient + border-radius"),
    ("\t\t\tbackground: #F5F5F5;\n\t\t\tborder-radius: 40rpx 40rpx 40rpx 40rpx;",
     "\t\t\tbackground: #FFF8F0;\n\t\t\tborder-radius: 32rpx;\n\t\t\tbox-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);",
     ".bottom F5F5F5 -> FFF8F0 + border 32 + shadow"),
])

# ==================== FILE 9: inviteFriends.vue ====================
print("\n=== File 9: inviteFriends.vue ===")
process_file(r"D:\xyapp\hyt-app\pages\promotion\inviteFriends.vue", [
    ("\t\t\tbackground: linear-gradient(180deg, #FDA18B 0%, #FEF3CF 47%);",
     "\t\t\tbackground: linear-gradient(180deg, #FFF5EC 0%, #FFF0E0 40%, #FFEBD6 100%);",
     ".main gradient"),
    ("\t\t\tcolor: #330600;",
     "\t\t\tcolor: #5D3A1A;",
     ".myInviteCode color 330600 -> 5D3A1A"),
    ("\t\t\tbackground: linear-gradient(90deg, #FDB5A6 0%, rgba(254, 216, 185, 0.5) 100%);",
     "\t\t\tbackground: linear-gradient(90deg, #F4A460 0%, rgba(244, 164, 96, 0.3) 100%);",
     ".myInviteCode gradient"),
    ("\t\t\tbackground: linear-gradient(180deg, #FF1E1E 0%, #FF8C2E 100%);\n\t\t\tbox-shadow: inset 0rpx -8rpx 8rpx 0rpx #FF452E;\n\t\t\tborder-radius: 56rpx 56rpx 56rpx 56rpx;",
     "\t\t\tbackground: linear-gradient(135deg, #E8783B, #F4A460);\n\t\t\tborder-radius: 200rpx;",
     ".shareBtn gradient + remove shadow + border 200rpx"),
    ("\t\t\t\tbackground: #F5F5F5;\n\t\t\t\tborder-radius: 20rpx 20rpx 20rpx 20rpx;",
     "\t\t\t\tbackground: #FFF8F0;\n\t\t\t\tborder-radius: 32rpx;\n\t\t\t\tbox-shadow: 0 6rpx 24rpx rgba(200, 130, 60, 0.07);",
     ".fillInCode-input F5F5F5 -> FFF8F0 + border 32 + shadow"),
    ("\t\t\t\tbackground: linear-gradient(180deg, #FF1E1E 0%, #FF8C2E 100%);\n\t\t\t\tbox-shadow: inset 0rpx -8rpx 8rpx 0rpx #FF452E;\n\t\t\t\tborder-radius: 56rpx 56rpx 56rpx 56rpx;",
     "\t\t\t\tbackground: linear-gradient(135deg, #E8783B, #F4A460);\n\t\t\t\tborder-radius: 200rpx;",
     ".fillInCode-btn gradient + remove shadow + border 200rpx"),
])

# ==================== FILE 10: teamManagement.vue ====================
print("\n=== File 10: teamManagement.vue ===")
process_file(r"D:\xyapp\hyt-app\pages\promotion\teamManagement.vue", [
    ("\t\t\t\t\t\tcolor: #2684FF;",
     "\t\t\t\t\t\tcolor: #E8783B;",
     ".body-item-right color 2684FF -> E8783B"),
])

# ==================== FILE 11: teamMembers.vue ====================
print("\n=== File 11: teamMembers.vue ===")
process_file(r"D:\xyapp\hyt-app\pages\promotion\teamMembers.vue", [
    ("\t\t\t\t\t\t\tcolor: #000000;\n\t\t\t\t\t\t}",
     "\t\t\t\t\t\t\tcolor: #5D3A1A;\n\t\t\t\t\t\t\tfont-weight: 700;\n\t\t\t\t\t\t}",
     ".body-item-body-top span color -> 5D3A1A + fw700"),
])

# ==================== FILE 12: transactionRecord.vue ====================
print("\n=== File 12: transactionRecord.vue ===")
process_file(r"D:\xyapp\hyt-app\pages\promotion\transactionRecord.vue", [
    ("\t\t\tbackground: linear-gradient(180deg, #FDF6EE 0%, #FDE7C1 100%);",
     "\t\t\tbackground: linear-gradient(180deg, #FFF5EC 0%, #FFF0E0 40%, #FFEBD6 100%);",
     ".content gradient"),
    ("\t\t\t\tcolor: #000000;\n\t\t\t\tmargin-left: 220rpx;",
     "\t\t\t\tcolor: #5D3A1A;\n\t\t\t\tfont-weight: 700;\n\t\t\t\tmargin-left: 220rpx;",
     ".title span color 000000 -> 5D3A1A + fw700"),
    ("\t\t\t\tbackground: #FFFFFF;\n\t\t\t\tborder-radius: 40rpx 40rpx 0rpx 0rpx;",
     "\t\t\t\tbackground: #FFF8F0;\n\t\t\t\tborder-radius: 40rpx 40rpx 0rpx 0rpx;",
     ".body background FFFFFF -> FFF8F0"),
])

print("\n=== ALL DONE ===")
