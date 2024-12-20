// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.0;
import "./DecentralizedCoin.sol";
import {ReentrancyGuard} from "./ReentrancyGuard.sol";


contract Transactions is ReentrancyGuard{

DecentralizedCoin private immutable i_dsc;
uint256 private constant PRECISION = 1e18;

    mapping(address user => uint256 amount) private s_DSC_BURNT;
    mapping(address user => uint256 amount) private s_DSC_MINTED;
mapping(address user => uint256 amount) private s_DSC_TRANSFERRED;



modifier moreThanZero(uint256 amount) {
if (amount == 0) {
revert DSC__NEEDS_MORE_THAN_ZERO();
}
_;
}
modifier isAllowedToken(address token) {
if (address(token) == address(0)) {
revert DSC__TOKEN_NOT_ALLOWED(token);
}
_;
}


error DSC__NEEDS_MORE_THAN_ZERO();
error DSC_MINT_FAILED();
error DSC_BURN_FAILED();
error DSC__TOKEN_NOT_ALLOWED(address token);
error DSC__TRANSFER_FAILED();

constructor(address dscAddress){
    /*passing the token's address in the constructor and casting it to the token contract type
    ensures that only this token is allowed for transaction.*/
i_dsc = DecentralizedCoin(dscAddress);

}

/*we directly mint coin to user based on specified amount in spring boot bank application.
    This function is used when a user wants to convert their cash into tokens.*/
function mintDsc(address to, uint256 amount) public moreThanZero(amount) nonReentrant {
s_DSC_MINTED[msg.sender] += amount;
bool minted = i_dsc.mint(to, amount * PRECISION);

if (minted != true) {
revert DSC_MINT_FAILED();
}
}
//Here we do not burn the tokens because since it is a transfer to another address, the tokens are still valid
//as they still represent cash at the bank
function transferFromUser1ToUser2(uint256 amount, address from, address to) public moreThanZero(amount) nonReentrant
{
s_DSC_TRANSFERRED[from] += amount;
i_dsc.approve(msg.sender, amount * PRECISION);
bool success = i_dsc.transferFrom(from, to, amount * PRECISION);

if (!success) {
revert DSC__TRANSFER_FAILED();
}



}
// used when a user converts all their coins into cash. We burn all transferred tokens here
function burnDsc(uint256 amount, address from) public nonReentrant moreThanZero(amount)   {
s_DSC_BURNT[from] += amount;
i_dsc.approve(msg.sender, amount * PRECISION);
bool success = i_dsc.burn(from, amount * PRECISION);

if (!success) {
revert DSC__TRANSFER_FAILED();
}


}

    function getTotalTokensTransferredByUser(address user)public view returns(uint256)  {
        return s_DSC_TRANSFERRED[user];
    }
    function getTotalTokensMintedToUser(address user)public view returns(uint256)  {
        return s_DSC_MINTED[user];
    }
    function getTotalTokensBurnt(address user)public view returns(uint256)  {
        return s_DSC_BURNT[user];
    }
}



