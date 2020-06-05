package com.example.reddittop50.domain

abstract class UseCase<out Type, in Params> {
    abstract suspend operator fun invoke(params: Params): Result<Type>
}